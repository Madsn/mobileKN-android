var mongoose = require('mongoose'),
  Schema = mongoose.Schema,
  ObjectId = Schema.Types.ObjectId;

// http://mongoosejs.com/docs/guide.html
var UserSchema = new Schema({ // id is available after save through 'id'
  name: String,
  is_parent: {
    type: Boolean,
    'default': true
  },
  parent: {
    type: ObjectId,
    required: false,
    ref: 'User'
  }
  // foreign keys - http://mongoosejs.com/docs/populate.html
});
var User = mongoose.model('User', UserSchema);
module.exports.User = User;

var TaskSchema = new Schema({
  name: String,
  done: Boolean,
  owner: {type: ObjectId, required: false, ref:'User'}
});
var Task = mongoose.model('Task', TaskSchema);
module.exports.Task = Task;