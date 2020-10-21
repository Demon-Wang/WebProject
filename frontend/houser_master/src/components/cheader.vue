<template>
    <div>
      <el-container >

        <el-header >

          <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
            <el-menu-item index="/">首页</el-menu-item>
            <el-menu-item index="/rentHouse">租房</el-menu-item>
            <el-menu-item index="/saleHouse">购房</el-menu-item>
            <el-menu-item index="/addHouse">发布房源</el-menu-item>
            <el-menu-item index="/msg">告示栏</el-menu-item>

            
            <el-submenu index="person" style="float: right" v-show="logoutFlag">
              <template slot="title">{{loginOrRegis}}</template>
                <el-menu-item index="/myHouse">我的房屋</el-menu-item>
                <el-menu-item index="/myMsg">我发布的信息</el-menu-item>
                <el-menu-item @click="logout">退出</el-menu-item>
            </el-submenu>
            <el-menu-item index="/login" style="float: right" v-show="!logoutFlag">登录注册</el-menu-item>
          </el-menu>

        </el-header>

      </el-container>

    </div>
</template>

<script>
    export default {
        name: "oldheader",
        data() {
            return {
                activeIndex: '1',
                activeIndex2: '1',
                input:'',
                loginOrRegis:'登录注册',
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
        mounted () {
          this.activeIndex =  this.$route.path
        },
        methods: {
          handleSelect(key, keyPath) {
            this.activeIndex = key
            this.$router.push({path: key})
          },
          logout(){
            window.sessionStorage.clear()
            this.$router.push('/')
          }
        }
    }
</script>

<style scoped>

</style>
