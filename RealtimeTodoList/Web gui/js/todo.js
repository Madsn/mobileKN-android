var chorp = angular.module('RealtimeTodoList', []);

chorp.controller('TodoCtrl', function TodoCtrl($scope) {
  sequence = 0;
  $scope.nextSequence = function(){
    sequence += 1;
    return sequence;
  };

  $scope.todos = [
    {id: $scope.nextSequence(), text:'Learn android development', done:true},
    {id: $scope.nextSequence(), text:'Make a killer android app', done:false},
    {id: $scope.nextSequence(), text:'???????', done:false},
    {id: $scope.nextSequence(), text:'Profit', done:false}];

  $scope.addTodo = function() {
    if ($scope.todoText.length > 0){
      $scope.todos.unshift({id: $scope.nextSequence(), text:$scope.todoText, done:false});
      $scope.todoText = '';
    }
  };

  $scope.toggleDone = function(select_id) {
    var oldTodos = $scope.todos;
    var addLast = [];
    $scope.todos = [];
    angular.forEach(oldTodos, function(todo) {
      if (todo.id === select_id) {
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
    angular.forEach(oldTodos, function(todo){
      if(!todo.done){
        $scope.todos.push(todo);
      }
    });
  };
});

