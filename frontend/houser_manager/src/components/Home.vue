<template>
  <el-container class="home-container">
    <el-header>
      <div>
        <img src="../assets/logo.png" height="80%" alt="">
        <span>房屋租赁管理系统</span>
      </div>
      <el-button type="info" @click="logout">退出</el-button>
    </el-header>
    <el-container>
      <el-aside :width="isCollapsed? '64px' : '200px'">
        <div class="toggle-button" @click="toggleCollapse">|||</div>
        <el-menu :default-active="activePath" router :collapse-transition="false" :collapse="isCollapsed" unique-opened background-color="#333744" text-color="#fff" active-text-color="#409eff">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>用户管理</span>
            </template>

            <el-menu-item @click="saveNavState('/admin')" index="/admin">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span>管理员列表</span>
              </template>
            </el-menu-item>

            <el-menu-item @click="saveNavState('/users')" index="/users">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span>用户列表</span>
              </template>
            </el-menu-item>

          </el-submenu>

          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-s-home"></i>
              <span>房屋管理</span>
            </template>

            <el-menu-item @click="saveNavState('/saleHouse')" index="/saleHouse">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span>出售房屋列表</span>
              </template>
            </el-menu-item>

            <el-menu-item @click="saveNavState('/rentHouse')" index="/rentHouse">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span>出租房屋列表</span>
              </template>
            </el-menu-item>

          </el-submenu>

          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-view"></i>
              <span>信息管理</span>
            </template>

            <el-menu-item @click="saveNavState('/msgRent')" index="/msgRent">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span>求租信息列表</span>
              </template>
            </el-menu-item>

            <el-menu-item @click="saveNavState('/msgSale')" index="/msgSale">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span>求购信息列表</span>
              </template>
            </el-menu-item>

          </el-submenu>

        </el-menu>
      </el-aside>

      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  export default {
    data(){
      return{
        isCollapsed:false,
        activePath:''
      }
    },
    created(){
      this.activePath = window.sessionStorage.getItem('activepath')
    },
    methods:{
      logout(){
        window.sessionStorage.clear()
        this.$router.push('/login')
      },
      toggleCollapse(){
        this.isCollapsed=!this.isCollapsed
      },
      saveNavState(activePath){
        window.sessionStorage.setItem('activepath',activePath)
        this.activePath=activePath
      }
    }
  };
</script>

<style lang="less" scoped>

.home-container{
  height: 100%;
}

.el-header{
  background-color: #373d41;
  display: flex;
  justify-content: space-between;
  padding-left: 10pt;
  align-items: center;
  color: #fff;
  font-size: 20px;
  >div{
    height: 100%;
    display: flex;
    align-items: center;
    span{
      margin-left: 15px;
    }
  }
}

.el-aside{
  background-color: #333744;
} .el-menu{
    border-right: none;
  }

.el-main{
  background-color: #eaedf1;
}

.toggle-button{
  background-color: #4a5064;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>
