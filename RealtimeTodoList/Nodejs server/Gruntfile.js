'use strict';
module.exports = function(grunt) {
  /*jshint maxstatements:40 */
  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    mochaTest: {
      files: ['test/*.js']
    },
    mochaTestConfig: {
      options: {
        reporter: 'spec',
        growl: true
      }
    },
    jshint: {
      options: {
        'indent': 2,
        'latedef': true,
        'newcap': true,
        'noarg': true,
        'noempty': false,
        'nonew': true,
        'quotmark': true,
        'undef': true,
        'unused': true,
        'trailing': true,
        'maxparams': 5,
        'maxdepth': 5,
        'maxstatements': 15,
        'maxcomplexity': 10,
        'bitwise': true,
        'eqeqeq': true,
        'immed': true,
        'forin': false,
        'curly': true,
        'globalstrict': true,
        'strict': true,
        'node': true,
        'maxlen': 78,
        'predef': [
          'default',  //used by express
          'describe',  // used by mocha
          'it',  // used by mocha
          'before',  // used by mocha
          'beforeEach',  // used by mocha
          'after', // used by mocha
          'afterEach', // used by mocha
          'done'  // used by mocha
        ]
      },
      all: ['./*.js', 'views/*.js', 'test/*.js']
    },
    watch: {
      lint: {
        files: ['./*.js', 'views/*.js', 'views/*.html', 'test/*.js'],
        tasks: ['jshint']
      },
      test:{
        files: ['./*.js', 'views/*.js', 'views/*.html', 'test/*.js'],
        tasks: ['mochaTest']
      }
    },
    shell: { // run system commands
      jitsudeploy: {
        command: 'echo remember to login first with "jitsu login" ' +
                 '&& jitsu deploy -c',
        options: {
          stdout: true,
          message: 'deploy to nodejitsu complete'
        }
      },
      herokudeploy: {
        command: 'cp ../.gitignore .gitignore && ' +
                 'git init && ' +
                 'git remote add heroku git@heroku.com:chorp.git &&' +
                 'git add . && git commit -m "sync" && ' +
                 'git push heroku --force && rm -rf .git && rm .gitignore',
        options: {
          stdout: true,
          stderr: true,
          stdin: true,
          message: 'deploy to heroku complete'
        }
      },
      appfogdeploy: { // requires ruby and "af" gem
        command: 'echo remember to login via "af login" first ' +
                 '&& af update noptech-node1',
        options: {
          stdout: true
        }
      },
      localdeploy: {
        command: 'nodemon start.js',
        options: {
          stdout: true
        }
      }
    }
  });
  var localdeploy =  'growl:Deploy complete:local';
  var appfogdeploy =  'growl:Deploy complete:Appfog';
  var herokudeploy =  'growl:Deploy complete:Heroku';
  var jitsudeploy = 'growl:Deploy complete:Nodejitsu';

  grunt.registerTask('growl', function(title, msg){
    var growl = require('growl');
    growl(msg, {'title': title});
  });

  grunt.loadNpmTasks('grunt-mocha-test');
  grunt.loadNpmTasks('grunt-shell');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-watch');

  // tasks
  grunt.registerTask('default', ['jshint', 'mochaTest']);

  // lint
  grunt.registerTask('lint', ['jshint']);
  grunt.registerTask('lintw', ['jshint', 'watch:lint']);
  grunt.registerTask('l', ['jshint']);
  grunt.registerTask('lw', ['jshint', 'watch:lint']);

  // tests
  grunt.registerTask('test', ['mochaTest']);
  grunt.registerTask('testw', ['mochaTest', 'watch:test']);
  grunt.registerTask('t', ['mochaTest']);
  grunt.registerTask('tw', ['mochaTest', 'watch:test']);

  // lint + tests
  grunt.registerTask('w', ['jshint', 'mochaTest', 'watch']);

  // deployment
  grunt.registerTask('heroku', ['shell:herokudeploy', herokudeploy]);
  grunt.registerTask('jitsu', ['shell:jitsudeploy', jitsudeploy]);
  grunt.registerTask('appfog', ['shell:appfogdeploy', appfogdeploy]);
  grunt.registerTask('local', ['shell:localdeploy', localdeploy]);
  grunt.registerTask('deployall', ['jitsu', 'appfog', 'heroku']);
  grunt.registerTask('growltest', [localdeploy]);

// http://blog.fgribreau.com/2012/06/how-to-get-growl-notifications-from.html
  var growl = require('growl');
  ['fail', 'warn', 'fatal'].forEach(function(level) {
    grunt.util.hooker.hook(grunt.fail, level, function(opt) {
      growl(opt.message, {
        title: opt.name
      });
    });
  });
};

