<template>
  <div class="cfooter">
    <div style="width: 60%;margin: 0 auto">
      <el-row style="color: #fff;line-height: 80px">
        <span>关于天大</span>
        <span>联系我们</span>
        <span>加入我们</span>
        <span>隐私声明</span>
        <span>网站地图</span>
        <span>友情链接</span>
        <span style="float: right;color: #7dcfa0;">官方客服158xxxx2067</span>
      </el-row>
      <el-row>
        <span class="content">天津大学 | 国际工程师学院 计算机科学技术专业 1班 | © Copyright©2019级 CZ</span>
        <br>
        <span class="content">违法和不良信息举报电话：+86-158xxxx2067 违法和不良信息举报邮箱：cz@tju.edu.cn</span>
      </el-row>
    </div>
  </div>
</template>

<script>
    export default {
      name: "cfooter"
    }
</script>

<style scoped>
.cfooter{
  width: 100%;
  padding-bottom: 20px;
  background: url("../assets/img/footer-bg.jpg");
}
  span{
    padding: 10px;
  }
  .content{
    color:#888c8e;
    line-height: 30px;
    font-size: 12px;
  }
</style>
