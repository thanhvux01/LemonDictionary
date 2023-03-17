const User = require("../model/User.js");
const CreateError = require('../utils/ErrorFormat');
const CreateRes = require('../utils/JsonResponseFormat');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');
require("dotenv").config();



const Register = async(req,res,next) => {
    try{
        const {username,password,email,birthday} = req.body;
        if(!username || !password ){
            return next(CreateError(400,"check your input field"));
        }   
        const exist = await User.findOne({username});
        if(exist){
            return next(CreateError(400,"username is already exist"));
        }
        const salt = bcrypt.genSaltSync(10);
        const hash = bcrypt.hashSync(password,salt);    
        const user = new User({
            username,
            password:hash,
            email,
            birthday,
        })
     await user.save();
     res.status(200).json(CreateRes(undefined,"Success"))
    }catch(err){
        console.log(err);
        next(err);
    }
}
const Login = async (req,res,next) => {
    try{
        
        const user = await User.findOne({username:req.body.username});     
        if(!user) 
        { 
          return res.status(404).json({error:"User not found",status:"404"}) ;
        }
        const comparePassword = await bcrypt.compare(req.body.password,user.password)
        if(!comparePassword) 
        {
          return res.status(400).json({error:"Wrong password",status:"400"});
        }
        const {_id,password,username,isAdmin,...rest} = user._doc; 
        const token = jwt.sign({id:_id,isAdmin},process.env.AUTH_KEY);
        res.status(200).send(token);
    }catch(err){
      console.log(err);
      next(err);
    }
  }

const AddFavorite = async (req,res) => {
    try{
        const {id} = req.user;
        const user = await User.findOne({_id:id});
        res.status(200).send(user);
    }catch(err){
         console.log(err);
         next(err);
    }
}

const Test = async(req,res) => {
    try{
       
        res.status(200).send("Hello world");

    }catch(err){
        console.log(err);
        next(err);
    }
}

module.exports = {Register,Login,Test,AddFavorite} ;