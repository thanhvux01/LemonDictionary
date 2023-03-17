const axios = require('axios');
const CreateError = require('../utils/ErrorFormat');
const Word = require('../model/Word');
const User = require("../model/User.js");
const CLIENT_ID = "GEUL96Gac2ER5fgV18hjH_wWWBV9tnEcMk3qXwbCjho";

   const UnsplashConfig = {
    method: 'GET',
    url: 'https://api.unsplash.com/search/photos',
    headers: { "Accept-Encoding": "gzip,deflate,compress" }, 
    params: {client_id:CLIENT_ID,
             query:"cat"
                                },
  };

  const RapidConfig = {
    method: 'POST',
    url: 'https://google-translate1.p.rapidapi.com/language/translate/v2',
    headers: {
      'content-type': 'application/x-www-form-urlencoded',
      'Accept-Encoding': 'application/gzip',
      'X-RapidAPI-Key': 'd56b8f9229msh777a850074cdfffp168e3djsnc72f4ee449da',
      'X-RapidAPI-Host': 'google-translate1.p.rapidapi.com'
    },
    data:""
  };
const WordInfo = async (req,res,next) => {
     
      try{
        const encodedParams = new URLSearchParams();
        encodedParams.append("target", "vi");
        encodedParams.append("source", "en");

        const WordObj = {
            name:"",
            phonetic:"",
            audioURL:"",
            partOfSpeech:"",
            definition:"",
            example:"",
            meaning:"",
            img:""
        }
          const {word} = req.query;
          const info = await axios.get(`https://api.dictionaryapi.dev/api/v2/entries/en/${word}`);
          if(info.title){
            return res.status(404).send(CreateError(404,"Word not found"));
          }
          const {phonetic,phonetics,meanings} = info.data[0];

           WordObj.name = word;
           WordObj.phonetic = phonetic;
           WordObj.audioURL = phonetics[0].audio;
           WordObj.partOfSpeech = meanings[0].partOfSpeech;
           WordObj.definition =  meanings[0].definitions[0].definition || "";
           WordObj.example =  meanings[0].definitions[0].example || "";

           encodedParams.append("q", word);
           RapidConfig.data = encodedParams;
           const translate = await axios.request(RapidConfig);
           if(translate)
           WordObj.meaning = translate["data"]["data"].translations[0].translatedText; 
           UnsplashConfig.params.query = word
           const img = await axios.request(UnsplashConfig); 
           WordObj.img = img["data"]["results"][0]["urls"]["regular"];
           res.status(200).send(WordObj);
          

      }catch(err){
            console.log(err);
            next(err);
      }
}


const Translate = async(req,res,next) =>{

       try{
        const {text,target="vi",source="en"} = req.query;
        const encodedParams = new URLSearchParams();
        encodedParams.append("target", target);
        encodedParams.append("source", source);
        encodedParams.append("q", text);
        RapidConfig.data = encodedParams;
          const response = await axios.request(RapidConfig);
          const result = response["data"]["data"].translations[0].translatedText;
          res.status(200).send(result);
        
       }catch(err){
           console.log(err);
           next(err);
       }
}
const AddFavorite = async (req,res,next) => {
  try{
      const {id} = req.user;
      console.log(req.body);
      const {audioURL,example,phonetic,partOfSpeech,meaning,name,img} = req.body;
      const user = await User.findOne({_id:id});
      if(!user){
        return res.status(404).next(CreateError(404,"User Expired"));
      }
      const wordObj = {
        name,
        meaning,
        phonetic,
        partOfSpeech,
        img,
        audioURL,
        example, 
      }
      user.favorite.push(wordObj);
      await user.save();
      return res.status(200).send("Success")
  }catch(err){
       console.log(err);
       next(err);
  }
}
const GetListFavorite = async(req,res,next) => {
  try{
    const{id} = req.user;
    
    const user = await User.findOne({_id:id});
    if(!user){
      return res.status(404).next(CreateError(404,"User Expired"));
    }
    return res.status(200).send(user.favorite);

  }catch(err){
    console.log(err);
    next(err);
  }
}
const Revision = async(req,res,next) => {
  try{
    const {id} = req.user;
    console.log(req.body);
    const {number,type,topic} = req.body;
    if(type==0){
      const word = await Word.find({});
      return res.status(200).send(word.slice(0,number));
    }
     res.status(200).send("Hello");
  }catch(err){
     console.log(err);
     next(err);
  }
}
const AddNewWord = async(req,res,next) => {
  try{

     let imgDev;
     let audioDev;
     let phoneticDev;
     const{name,meaning,audioURL,img,phonetic,partOfSpeech="noun",example,definition,topic} = req.body;
   
     if(!img){
      UnsplashConfig.params.query = name
      const store = await axios.request(UnsplashConfig); 
      imgDev = store["data"]["results"][0]["urls"]["regular"];
     }else{
      imgDev = img;
     }
     
     if(!audioURL){
      const info = await axios.get(`https://api.dictionaryapi.dev/api/v2/entries/en/${name}`);
      const {phonetics} = info.data[0];     
      audioDev = phonetics[0].audio;
     }else{
       audioDev = audioURL;
     }
     if(!phonetic){
      const info = await axios.get(`https://api.dictionaryapi.dev/api/v2/entries/en/${name}`);
      const {phonetic} = info.data[0];     
      phoneticDev = phonetic;
     }else{
      phoneticDev = phonetic
     }
     const word = new Word({
        name,
        meaning,
        audioURL,
        img:imgDev,
        audioURL:audioDev,
        phonetic:phoneticDev,
        partOfSpeech,
        example,
        definition,
        topic,
     })
     await word.save();
    res.status(200).send("Success");
  }catch(err){
    console.log(err);
    next(err);
  }
}
module.exports = {WordInfo,Translate,AddFavorite,GetListFavorite,AddNewWord,Revision};