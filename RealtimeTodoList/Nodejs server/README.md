# Chorp #

## Gruntfile.js ##
Configuration file for [grunt](http://gruntjs.com/), a javascript task runner. Automates deployment, tests and so on via grunt tasks, e.g. 'grunt test'

### Deploy targets ###

#### Nodejitsu
Requirements: `npm install -g jitsu`

Prior to running `grunt jitsu` you must: `jitsu login`

#### Appfog
Requirements: Ruby and the `af` gem - `gem install af`

Prior to running `grunt appfog`: `af login`

#### Heroku
Requirements: Heroku toolbelt NOT required unless modifying config.

Prior to running `grunt heroku` (first time only): `git remote add heroku git@heroku.com:madsn.git`


## start.js ##
Launches app. To start, run 'node start.js', or 'nodemon start.js' for automatic restart on file changes when developing.

## package.json ##
Specifies dependencies and app info, used by hosts to install dependencies

## growlnotify.exe ##
Needed for notifications via growl for grunt tasks

### note ###
[growl for windows](http://www.growlforwindows.com/gfw/) must be installed if you want the notifications

## Procfile ##
Purely for [heroku](https://www.heroku.com/) deploys
