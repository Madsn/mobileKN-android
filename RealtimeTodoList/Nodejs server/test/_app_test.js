'use strict';

var io = require('socket.io-client');
var assert = require('chai').assert;
var models = require('../models');
var Task = models.Task;
// var User = models.User;
var port = 1337;
var url = 'http://localhost';
describe('Server sockets -', function(){
  this.timeout(5000);

  var app = require('../app.js');
  before(function(done){
    app.start(port, function(){
      console.log('app started for tests');
      app.deleteAllTasks(function(err){
        if (err){console.log(err.name + '\n' + err.reason);}
      });
      done();
    });
  });

  after(function(){
    app.stop();
  });

  describe('Tasks -', function(){

    var length;
    var taskIds = [];
    var socket;
    var socketOptions = {'reconnection delay' : 0,
        'reopen delay' : 0,
        'force new connection' : true,
        'close timeout' : 10};

    beforeEach(function(done){
      socket = io.connect(url + ':' + port, socketOptions);
      length = 0;
      socket.on('taskList', function(data){
        var tasks = JSON.parse(data).tasks;
        length = tasks.length;
      });
      socket.on('msg', function() {
        // console.log('Server error: ' + data.msg);
      });
      socket.on('connect', function() {
        // console.log('connection established');
        done();
      });
    });

    afterEach(function(done){
      if(socket.socket.connected) {
        socket.disconnect();
        // console.log('disconnected');
        done();
      }
    });

    it('adding first task', function(done){
      var t = new Task({name:'My test task', desc:'My description'});
      socket.on('newTasks', function(data){
        var tasks = JSON.parse(data).tasks;
        taskIds.push(tasks[0]._id);
        assert.isNotNull(taskIds[0], 'verify ID was received');
        assert.isDefined(taskIds[0], 'verify ID defined');
        assert.equal(tasks[0].name, t.name);
        assert.equal(tasks[0].desc, t.desc);
        done();
      });
      socket.emit('addTasks', JSON.stringify({tasks: [t]}));
    });

    it('adding multiple tasks (single and array)', function(done){
      var t = new Task({name:'My test task2',
                        desc:'My description for task 2'});
      var t2 = new Task({name:'My test task3',
                        desc:'My description for task 3'});
      var i = 0;
      socket.on('newTasks', function(data){
        data = JSON.parse(data);
        taskIds.push(data.tasks[0]._id);
        // console.log(taskIds);
        i++;
        if (i === 5){
          assert.equal(6, taskIds.length, 'All sent tasks were seen');
          done();
        }
      });
      socket.emit('addTasks', JSON.stringify({tasks: t}));
      socket.emit('addTasks', JSON.stringify({tasks: [t2, t]}));
      // add a couple of duplicates
      socket.emit('addTasks', JSON.stringify({tasks: t2}));
      socket.emit('addTasks', JSON.stringify({tasks: [t]}));
      socket.emit('addTasks', JSON.stringify({tasks: t2}));
    });


    it('getting tasklist', function(done){
      // setTimeout(function(){
      socket.emit('getTaskList', {});
      // }, 1000);
      setTimeout(function(){
        assert.equal(6, length, 'Verifying 6 tasks exist in list');
        done();
      }, 500);
    });

    it('deleting single task (not array)', function(done){
      socket.emit('delTasks', JSON.stringify({'tasks': taskIds[0]}));
      setTimeout(function(){
        socket.emit('delTasks', JSON.stringify({'tasks': taskIds[0]}));
      }, 200);
      setTimeout(function(){
        assert.equal(5, length,
          'Verifying 5 tasks exist in list');
        done();
      }, 500);
    });

    it('deleting single task (array)', function(done){
      socket.emit('delTasks', JSON.stringify({'tasks': taskIds[1]}));
      setTimeout(function(){
        socket.emit('delTasks', JSON.stringify({'tasks': taskIds[1]}));
      }, 200);
      setTimeout(function(){
        assert.equal(4, length,
          'Verifying 4 tasks exist in list');
        done();
      }, 500);
    });

    it('deleting two tasks (array)', function(done){
      var tasks = [];
      tasks.push(taskIds[2]);
      tasks.push(taskIds[3]);
      socket.emit('delTasks', JSON.stringify({'tasks': tasks}));
      setTimeout(function(){
        socket.emit('delTasks', JSON.stringify({'tasks': tasks}));
      }, 200);
      setTimeout(function(){
        assert.equal(2, length, 'Verifying 2 tasks exist in list');
        done();
      }, 500);
    });

    it('deleting all tasks', function(done){
      socket.emit('clearTasks', JSON.stringify({}));
      setTimeout(function(){
        socket.emit('cleartasks', JSON.stringify({}));
      }, 200);
      setTimeout(function(){
        assert.equal(0, length,
          'Verifying 0 tasks exist in list');
        done();
      }, 500);
    });


  });

  describe('Users -', function(){
    // var parent1, parent2, child1, child2;
    // var names = ['John doe', 'Jane doe',
    //             'Mikkel Madsen', 'Nikolaj Røndbjerg'];
    before(function(){

    });
    it('creation', function(){
      // assert(false);
    });
    it('modificaton', function(){
      // assert(false);
    });
    it('deletion', function(){
      // assert(false);
    });
  });
});

