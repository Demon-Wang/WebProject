<template>
  <div>
    <el-container class="cbody">
      <el-header>
        <el-row class="cheader" type="flex" justify="center">
          <el-col
            :span="4"
            style="font-size: 32px;cursor: pointer;font-weight: bold;font-family: Arial, Helvetica, 'Microsoft YaHei', Tohoma, sans-serif"
          >天大房产</el-col>
          <el-col :span="1" :offset="13" style="cursor: pointer">
            <span @click="saveNavState('/rentHouse')">租房</span>
          </el-col>
          <el-col :span="1" style="cursor: pointer">
            <span @click="saveNavState('/saleHouse')">买房</span>
          </el-col>
          <el-col :span="2" style="cursor: pointer">
            <span @click="saveNavState('/addHouse')">发布房源</span>
          </el-col>
          <el-col :span="2" style="cursor: pointer">
            <span @click="saveNavState('/msg')">告示栏</span>
          </el-col>
          <el-col :span="2" style="cursor: pointer">
            <span @click="saveNavState('/login') " v-show="!logoutFlag">登录注册</span>
            <span v-show="logoutFlag">{{loginOrRegis}}</span>
          </el-col>
        </el-row>
      </el-header>
      <el-main style="padding-top: 100px;">
        <el-row type="flex" justify="center">
          <el-col :span="8" style="text-align: center">
            <h1 style="color: #fff;font-size: 48px;">为你找寻一个家</h1>
          </el-col>
        </el-row>
        <el-row type="flex" justify="center">
          <el-col :span="8" style="text-align: center">
            <span style="color:#fff;cursor:pointer;padding-left:20px;" @click="searchType('rentHouse')">找租房</span>
            <span style="color:#fff;cursor:pointer;padding-left:20px;" @click="searchType('saleHouse')">找二手房</span>
            <span style="color:#fff;cursor:pointer;padding-left:20px;" @click="searchType('Msg')">找信息</span>
          </el-col>
        </el-row>

        <el-row class="csearch" type="flex" justify="center">
          <el-col :span="8">
            <el-input
              :placeholder="searchPlaceHolder"
              v-model="searchContent"
              class="input"
              style="border-radius: 0px;"
            ></el-input>
          </el-col>
          <el-button type="success" class="cbtn-bg" @click="searchHouse">开始找房</el-button>
        </el-row>
      </el-main>
    </el-container>
    <el-container>
      <div style="width:50%;margin:20px auto;height: 200px;">
        <el-row>
          <el-col :span="12" style="text-align: center;">
            <el-row>
              <el-image
                :src="require('../assets/salehouse.jpg')"
                style="width:80px;height:80px"
              ></el-image>
            </el-row>
            <el-row style="line-height:40px;font-size:18px;">
              <span @click="saveNavState('/saleHouse')" style="cursor:pointer">找二手房</span>
            </el-row>
            <el-row style="line-height:40px;font-size:12px;color:grey">真实二手房源，万径承诺真实在售 所见即真</el-row>
          </el-col>
          <el-col :span="12" style="text-align: center;" >
            <el-row>
              <el-image :src="require('../assets/renthouse.jpg')" style="width:80px;height:80px"></el-image>
            </el-row>
            <el-row style="line-height:40px;font-size:18px;" >
              <span @click="saveNavState('/renthouse')" style="cursor:pointer" >找租房</span>
            </el-row>
            <el-row style="line-height:40px;font-size:12px;color:grey">好房子，尽在于此 为自己找个更好的安身之所</el-row>
          </el-col>
        </el-row>
      </div>
    </el-container>
    <el-row class="cefooter">
      <el-col :span="10" :offset="4">
        <el-row>
          <h1 style="color: #fff;font-size: 50px;">天大真实房源</h1>
        </el-row>
        <el-row style="color: #fff;font-size: 30px">不是嘴上说说就可以</el-row>
      </el-col>
      <el-col :span="10">
        <el-row>
          <span style="color: #fff;font-size: 16px;line-height:100px;">真实存在，真实在售，真实价格，真实图片！</span>
        </el-row>
        <el-row style="color: #fff;font-size: 16px">郑重向用户承诺：真实房源，假一赔百元！</el-row>
      </el-col>
    </el-row>
    <cfooter></cfooter>
  </div>
</template>
<script>
import cfooter from "@/components/cfooter";
export default {
  name: "index",
  components: {
    cfooter: cfooter
  },
  data() {
    return {
      searchPlaceHolder:'请输入小区名称开始找房',
      houseType: 'rentHouse',
      searchContent: '',
      loginOrRegis: '登录注册',
      logoutFlag: false
    };
  },
  created() {
      if(window.sessionStorage.getItem('token')) {
        this.logoutFlag = true
        this.loginOrRegis = window.sessionStorage.getItem('name')
      }
      else{
      }
  },
  mounted() {
  },
  methods: {
    saveNavState(activePath){
      this.$router.push(activePath)
    },
    handleSelect(key, keyPath) {
      this.$router.push({ path: key });
    },
    searchType(type) {
      this.houseType = type;
      if(type === 'rentHouse') {
        this.searchPlaceHolder = '请输入小区名称开始找在租房';
      }else if(type === 'saleHouse') {
        this.searchPlaceHolder = '请输入小区名称开始找在售房';
      }else{
        this.searchPlaceHolder = '请关键词开始找已发布信息';
      }
    },
    searchHouse(){
      this.$router.push({path:"/"+this.houseType, query:{ text: `${this.searchContent}` }})
    }
  }
};
</script>

<style scoped>
.cbody {
  background-image: url("../assets/img/bannerV2.jpg");
  width: 100%;
  height: 700px;
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
.cheader {
  width: 100%;
  line-height: 100px;
  color: #fff;
  text-align: center;
}
.csearch {
  width: 100%;
  padding-top: 10px;
}
.el-input__inner {
  border-radius: 0px;
}
.capp {
  width: 100%;
  height: 600px;
  background-image: url("../assets/img/bg-app.jpg");
  background-repeat: no-repeat;
}
.cbtn-bg {
  background: #00ae66;
  border: none;
  border-radius: 0px;
  height: 50px;
  width: 140px;
  font-size: 18px;
}
.cefooter {
  width: 100%;
  height: 260px;
  background-image: url("../assets/img/truth-bgV2.jpg");
}

.input >>> .el-input__inner {
  height: 50px;
  border-radius: 0px;
}

.crow {
  padding: 10px 0px;
}

.citem{
  background: none;
  color: #fff;
}
</style>
