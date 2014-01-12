var chorp = angular.module('RealtimeTodoList', []);

chorp.controller('TodoCtrl', function TodoCtrl($scope) {

  $scope.socket = io.connect(document.URL);

  $scope.todos = [];

  $scope.addTodo = function() {
    if ($scope.todoText.length > 0){
      $scope.socket.emit('addTasks', JSON.stringify({tasks:[{name:$scope.todoText, done:false}]}));
      $scope.todoText = '';
    }
  };

  $scope.addTodoFromServer = function(task) {
    console.log("Task received: ");
    console.log(task);
    $scope.todos.unshift({_id: task._id, name:task.name, done:task.done});
    $scope.$digest();
  };

  $scope.toggleDone = function(select_id){
    $scope.toggleDoneRearrange(select_id);
    var changed_task = $scope.getById(select_id);
    $scope.socket.emit('setDoneState', JSON.stringify({tasks: [changed_task]}));
  };

  $scope.getById = function(select_id){
    for (var i in $scope.todos){
      if ($scope.todos[i]._id == select_id){
        return $scope.todos[i];
      }
    }
  };

  $scope.toggleDoneRearrange = function(select_id) {
    var oldTodos = $scope.todos;
    var addLast = [];
    var changed_task;
    $scope.todos = [];
    angular.forEach(oldTodos, function(todo) {
      if (todo._id === select_id) {
        if (todo.done){
          $scope.todos.unshift(todo);
        } else {
          addLast.unshift(todo);
        }
        todo.done = !todo.done;
      } else {
        if (todo.done){
          addLast.push(todo);
        } else {
          $scope.todos.push(todo);
        }
      }
    });
    angular.forEach(addLast, function(todo){
      $scope.todos.push(todo);
    });
  };

  $scope.deleteCompleted = function() {
    var oldTodos = $scope.todos;
    $scope.todos = [];
    angular.forEach(oldTodos, function(todo){
      if(!todo.done){
        $scope.todos.push(todo);
      }else{
        $scope.socket.emit('delTasks', JSON.stringify({tasks:[todo]}));
      }
    });
  };

  $scope.socket.on('connection', function(socket){
    console.log('Connected to server');
    socket.emit('msg', {msg: 'Client connected'});
  });

  $scope.socket.on('msg', function(data) {
    data = JSON.parse(data);
    console.log('Msg received from server:')
    console.log(data.msg);
  });

  $scope.socket.on('taskList', function(data){
    data = JSON.parse(data);
    console.log('received updated tasklist from server');
    console.log(data.tasks);
    $scope.todos = [];
    for (var index in data.tasks){
      $scope.addTodoFromServer(data.tasks[index]);
    }
  });

  $scope.socket.on('newTasks', function(data){
    data = JSON.parse(data);
    for (var index in data.tasks){
      $scope.addTodoFromServer(data.tasks[index]);
    }
  });

  $scope.socket.on('delTasks', function(data){
    data = JSON.parse(data);
    var oldTodos = $scope.todos;
    $scope.todos = [];
    console.log('delTasks received:');
    console.log(data);
    console.log(data.tasks);
    for (var i in data.tasks){
      for (var localIndex in oldTodos){
        if (data.tasks[i]._id != oldTodos[localIndex]._id){
          $scope.todos.push(oldTodos[localIndex]);
        }
      }
    }
    $scope.$digest();
  });

  $scope.socket.on('setDoneState', function(data){
    data = JSON.parse(data);
    console.log('received setDoneState');
    console.log(data.tasks[0]);
    for (var i in data.tasks){
      for (var j in $scope.todos){
        if ($scope.todos[j]._id == data.tasks[i]._id){
          $scope.todos[j].done = data.tasks[i].done;
        }
      }
    }
    $scope.$digest();
  });

});
