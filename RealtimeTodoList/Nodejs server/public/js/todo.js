var chorp = angular.module('RealtimeTodoList', []);

chorp.controller('TodoCtrl', function TodoCtrl($scope) {

  $scope.socket = io.connect("127.0.0.1:3000");

  $scope.deleting = false;

  $scope.todos = [];

  $scope.addTodo = function() {
    if ($scope.todoText.length > 0){
      //$scope.todos.unshift({id: $scope.nextSequence(), text:$scope.todoText, done:false});
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

  $scope.toggleDone = function(select_id) {
    var oldTodos = $scope.todos;
    var addLast = [];
    $scope.todos = [];
    angular.forEach(oldTodos, function(todo) {
      if (todo._id === select_id) {
        if (todo.done){
          todo.done = false;
          $scope.todos.unshift(todo);
        } else {
          todo.done = true;
          addLast.unshift(todo);
        }
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
    $scope.deleting = true;
    angular.forEach(oldTodos, function(todo){
      if(!todo.done){
        $scope.todos.push(todo);
      }else{
        $scope.socket.emit('delTasks', JSON.stringify({tasks:todo._id}));
      }
    });
    $scope.deleting = false;
  };

  $scope.socket.on('connection', function(socket){
    console.log('Connected to server');
    socket.emit('msg', {msg: 'Client connected'});
  });

  $scope.socket.on('msg', function(data) {
    console.log('Msg received from server:')
    console.log(data.msg);
  });

  $scope.socket.on('taskList', function(data){
    data = JSON.parse(data);
    console.log('received updated tasklist from server');
    console.log(data.tasks);
    $scope.todos = [];
    if(!$scope.deleting){
      for (var index in data.tasks){
        $scope.addTodoFromServer(data.tasks[index]);
      }
    }
  });

  $scope.socket.on('newTasks', function(data){
    data = JSON.parse(data);
    for (var index in data.tasks){
      $scope.addTodoFromServer(data.tasks[index]);
    }
  });

});

