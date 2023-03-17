var express = require('express');
var router = express.Router();

const {WordInfo,Translate,AddFavorite,GetListFavorite,AddNewWord,Revision} = require("../controllers/WordController");
const {VerifyToken} = require("../utils/VerifyToken");
router.get("/search",WordInfo);
router.get("/translate",Translate);
router.post("/add-favorite",VerifyToken,AddFavorite);
router.get("/list-favorite",VerifyToken,GetListFavorite);
router.post("/add-word",VerifyToken,AddNewWord);
router.post("/revision",VerifyToken,Revision);







module.exports = router;
