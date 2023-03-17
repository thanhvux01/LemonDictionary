var express = require('express');
var router = express.Router();

const {Register,Login,Test} = require("../controllers/UserController");
const {VerifyToken} = require("../utils/VerifyToken");

router.post("/register",Register);
router.post("/login",Login);
router.get("/test",VerifyToken,Test);










module.exports = router;