const mongoose = require('mongoose');
const { Schema } = mongoose;



const WordSchema = new Schema({
    name:{type:String,unique:true},
    meaning:{type:String},
    phonetic:{type:String},
    img:{type:String},
    audioURL:{type:String},
    partOfSpeech:{type:String},
    definition:{type:String},
    topic:{type:String}
})

module.exports = mongoose.model('Word',WordSchema);