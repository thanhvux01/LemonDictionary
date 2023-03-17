const mongoose = require('mongoose');
const {DateToString} = require('../utils/DateFormat');
const { Schema } = mongoose;

const WordSchema = new Schema({
    name:{type:String,unique:true},
    meaning:{type:String},
    phonetic:{type:String},
    img:{type:String},
    audioURL:{type:String},
    partOfSpeech:{type:String},
    definition:{type:String}
})

const UserSchema = new Schema({
    username:{type:String,required:true,unique:true},
    password:{type:String,required:true},
    email:{type:String,required:true,unique:true},
    birthday:{type:String},
    isAdmin:{Boolean,default:false},
    createAt:{type:String,default:DateToString()},
    updateAt:{type:String,default:DateToString()},
    favorite:[WordSchema]
});


module.exports = mongoose.model('Users',UserSchema);
