/*
      SETUP
 */
'use strict';

var express = require('express'),
  http = require('http'),
  app = express(),
  server = http.createServer(app),
  path = require('path'),
  io = require('socket.io').listen(server),
  mongoose = require('mongoose'),
  models = require('./models');


// define models
var Task = models.Task;

app.get('/', function(req, res){
  res.sendfile('views/index.html');
});

/*
      TASK MANAGEMENT
 */
var getAllTasks = function(callback) {
  Task.find({}).exec(function(err, fetchedTasks){
    if (err) {
      callback(err);
    }else{
      callback(0, fetchedTasks);
    }
  });
};
exports.getAllTasks = getAllTasks;

var addTasks = function(tasks, callback) {
  // TODO - support adding multiple tasks?
  var task;
  if (!(tasks instanceof Array)){
    // console.log('*********************');
    // console.log(tasks);
    task = new Task({name:tasks.name, done:tasks.done});
    // console.log(task);
  }else{
    for (var i in tasks){
      task = new Task({name:tasks[i].name, done:tasks[i].done});
    }
  }
  task.save(callback);
};
//exports.addTasks = addTasks;

var deleteTasks = function(tasks, callback) {
  var delTaskCB = function(err){
    if (err){
      callback(err);
    }
  };
  if (!(tasks instanceof Array)){
    Task.remove({_id:tasks}).exec(delTaskCB);
  }else{
    for (var index in tasks){
      Task.remove({_id:tasks[index]}).exec(delTaskCB);
    }
  }
  callback(null);
};
exports.deleteTasks = deleteTasks;

var deleteAllTasks = function(callback) {
  Task.remove({}).exec(function(err){
    callback(err);
  });
};
exports.deleteAllTasks = deleteAllTasks;

/*
      SOCKETS
 */

io.sockets.on('connection', function(socket){
  // TODO: handle auth on connection
  var sendTaskList = function(){
    getAllTasks(function(err, allTasks){
      if (err){
        socket.emit('msg', {msg: 'error:\n' + err.name + ' ' + err.reason});
      }else{
        if (allTasks === null || allTasks === undefined){
          allTasks = [];
        }
        io.sockets.emit('taskList', JSON.stringify({tasks: allTasks}));
      }
    });
  };

  sendTaskList();

  socket.emit('msg', {msg:'server says: connection established'});

  socket.on('msg', function(data){
    console.log(data.msg);
  });

  socket.on('getTaskList', sendTaskList);

  var addTasks = function(tasks, callback) {
    // TODO - support adding multiple tasks?
    var taskList = [];
    if (!(tasks instanceof Array)){
      // console.log('*********************');
      // console.log(tasks);
      var task = new Task({name:tasks.name, done:tasks.done});
      task.save(function (err) {if (err) console.log ('Error on save!')});
      taskList.push(task);
      // console.log(task);
    }else{
      for (var i in tasks){
        var task = new Task({name:tasks[i].name, done:tasks[i].done});
        task.save(function (err) {if (err) console.log ('Error on save!')});
        taskList.push(task);
      }
    }
    callback(null, taskList);
  };

  socket.on('addTasks', function(data){
    data = JSON.parse(data);
    // console.log(data);
    if (data.hasOwnProperty('tasks')){
      // var tasks = JSON.parse(data.tasks);
      // console.log(JSON.parse(data.tasks));
      addTasks(data.tasks, function(err, savedTasks){
        if (err){
          socket.emit('msg', {msg: 'error:\n' + err.name + ' ' + err.reason});
        }else{
          io.sockets.emit('newTasks', JSON.stringify({tasks: savedTasks}));
        }
      });
    }
  });
  socket.on('clearTasks', function(){
    deleteAllTasks(function(err){
      if (err){
        socket.emit('msg',
          JSON.stringify({msg: 'error while attempting to delete tasks: ' +
          err.name + '\n' + err.description}));
      } else{
        io.sockets.emit('taskList', JSON.stringify({tasks: []}));
      }
    });
  });
  socket.on('delTasks', function(data){
    data = JSON.parse(data);
    if (data.hasOwnProperty('tasks')){
      deleteTasks(data.tasks, function(err){
        if (err){
          socket.emit('msg', JSON.stringify({msg:
            'error while attempting to delete task: ' +
            err.name + ' - ' + err.reason}));
        }else{
          sendTaskList();
        }
      });
    }
  });
  socket.on('close', function(){
    // remove client from subscriber pool
  });
});

/*
      SERVER START/STOP
 */
var commonSetup = function(){
  app.set('views', __dirname + '/views');
  app.set('port', process.env.PORT || 3000);
  // app.set('view engine', 'jade');
  app.use(express.favicon());
  app.use(express.bodyParser());
  app.use(express.methodOverride());
  app.use(app.router);
  app.use(express.static(path.join(__dirname, 'public')));
};

var start = function(port, callback) {
  commonSetup();
  if ('production' === process.env.NODE_ENV) {
    mongoose.connect('mongodb://noptech:r2d2r2d2' +
      '@dharma.mongohq.com:10033/noptech-sandbox');
  }else{
    // development only
    app.use(express.logger('dev'));
    app.use(express.errorHandler());
    mongoose.connect('mongodb://noptech:r2d2r2d2' +
        '@dharma.mongohq.com:10033/noptech-sandbox');
    /*
    mongoose.connect('mongodb://noptech:r2d2r2d2' +
      '@widmore.mongohq.com:10010/noptech-sandbox2');
*/
  }
  // set port
  if(port === null){
    port = app.get('port');
  }else if (port === 1337){
    io.set('log level', 0);
  }
  // Heroku and appfog don't support websockets
  // so instead of waiting for fallback,
  // use xhr-polling
  if(process.env.NODE_HOST === 'heroku' ||
    process.env.NODE_HOST === 'appfog'){
    io.configure(function () {
      io.set('transports', ['xhr-polling']);
      io.set('polling duration', 15);
    });
  }
  server.listen(port, callback);
};
exports.start = start;
var stop = function() {
  server.close();
};
exports.stop = stop;
