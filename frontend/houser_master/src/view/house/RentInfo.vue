<template>
    <div>
      <cheader></cheader>
      <div style="height: 80px;background: #f5f5f6;width: 100%">
        <div style="width: 60%;margin:  0 auto;">
          <el-row>
            <el-col :span="5" style="padding-left:0px;">
              <h2 style="color: #00ae66;">天大房产</h2>
            </el-col>
          </el-row>
        </div>
      </div>
      <div style="width: 60%;margin: 0 auto;">
      <el-container>
        <el-main >
          <el-row>
            <h1>{{Form.name}}</h1>
          </el-row>
          <el-row>
            <span style="color:#606266;line-height: 30px;">房源维护时间：{{Form.createTime | dateFormat}} </span><br>
            <span style="color:#606266">房源编号：{{Form.id}} </span>
          </el-row>
          <el-row style="margin-top: 20px;" :gutter="50">
            <el-col :span="14" >
              <div style="width:100%;height:400px;">
                <el-image :src="imageList[0]" style="width: 100%;height: 100%"></el-image>
              </div>
            </el-col>
            <el-col :span="10" >
              <div style ="width:100%;height:400px;">
                <el-row>
                  <el-col>
                    <span style="font-weight: bold;font-size: 30px;color: red">{{Form.price}}元/月</span>
                  </el-col>
                </el-row>
                <el-row style="margin-top: 20px">
                  <el-col>
                    <template>
                        <span v-if="Form.share===1">合租</span>
                        <span v-else >整租</span>
                    </template>
                  </el-col>
                </el-row>
                <el-divider></el-divider>
                <el-row>
                  <el-col>
                    <span style="color: #606266;line-height: 26px;">房型：<span>{{Form.framework}}</span></span>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <span style="color: #606266;line-height: 26px;">所在楼层：<span>{{Form.currentFloor}}</span></span>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <span style="color: #606266;line-height: 26px;">总楼层：<span>{{Form.totalFloor}}</span></span>
                  </el-col>
                </el-row>
                <el-divider></el-divider>
                <el-row>
                  <el-col>
                    <span style="color: #606266;line-height: 26px;" v-if="Form.share===1">合租要求：<span>{{Form.requirements}}</span></span>
                  </el-col>
                </el-row>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">真实存在，真实在租，真实价格，假一赔百</el-col>
          </el-row>
          <el-row>
            <h2>房屋信息</h2>
            <el-row>
              <el-col :span="8">
                <el-row style="line-height: 30px;">租房方式：
                    <span style="color:#000000;" v-if="Form.share===1">合租</span>
                    <span style="color:#000000;" v-else >整租</span>
                </el-row>
                <el-row style="line-height: 30px;">房屋户型：{{Form.framework}}</el-row>
                <el-row style="line-height: 30px;">委托人：{{Form.userId}}</el-row>
                <el-row style="line-height: 30px;">联系方式：{{Form.remarks}}</el-row>
                <el-row style="line-height: 30px;">房屋面积：{{Form.area}}㎡</el-row>
                <el-row style="line-height: 30px;">配套电梯：
                  <span style="color:#000000;" v-if="Form.elevator===1">有电梯</span>
                  <span style="color:#000000;" v-else >无电梯</span>
                </el-row>

              </el-col>
              <el-col :span="6">
                <el-row style="line-height: 30px;" v-if="Form.share===1">合租要求：{{Form.requirements}}</el-row>
                <el-row style="line-height: 30px;">所在楼层：{{Form.currentFloor}}</el-row>
                <el-row style="line-height: 30px;">总楼层：{{Form.totalFloor}}</el-row>
                <el-row style="line-height: 30px;">家具家电：{{Form.furniture}}</el-row>
                <el-row style="line-height: 30px;">装修情况：
                  <span style="color:#000000;" v-if="Form.renovation===0">清水</span>
                  <span style="color:#000000;" v-else-if="Form.renovation===1">简装</span>
                  <span style="color:#000000;" v-else >精装</span>
                </el-row>
                <el-row style="line-height: 30px;">最短租期：{{Form.leaseTerm}}个月</el-row>
              </el-col>
            </el-row>
          </el-row>
          <el-row>
            <h2>房源户型图</h2>
            <el-row style="margin-bottom: 20px;">
              <el-col :span="8" style="line-height: 28px" v-for="item in floorList" :key="item.id">
                <el-image :src="item" style="width: 100%;height: 100%"></el-image>
              </el-col>
            </el-row>
          </el-row>
          <el-row>
            <h2>房源照片</h2>
            <el-row style="margin-bottom: 20px;" :gutter="50">
              <el-col :span="8" style="line-height: 28px" v-for="item in imageList" :key="item.id">
                <el-image :src="item" style="width: 100%;height: 100%"></el-image>
              </el-col>
            </el-row>
          </el-row>
        </el-main>
        </el-container>
      </div>
    <cfooter></cfooter>
    </div>


</template>

<script type="text/javascript">
import cheader from "@/components/cheader";
import cfooter from "@/components/cfooter";
export default {
  components: {
    cheader: cheader,
    cfooter: cfooter
  },
  data() {
    return {
      Form: {
      },
      floorList:[
      ],
      imageList:[
      ]
    };
  },
  created() {
    var id = this.$route.query.id
    this.getHouseById(id);
  },
  methods: {
    async getHouseById(id){
      const { data:res } = await this.$http.get(`house/getHouseByHouseId/${id}`);
      if(res.code !== 1000) return this.$message('查询房屋信息失败')
      this.Form = res.data
      this.floorList = this.Form.floorPlan.split(';')
      for(var i = 0;i<this.floorList.length;i++){
        if(this.floorList[i]==''||this.floorList[i]==null||typeof(this.floorList[i])==undefined){
          this.floorList.splice(i,1);
          i=i-1;
        }
      }
      this.imageList = this.Form.images.split(';')
      for(var i = 0;i<this.imageList.length;i++){
        if(this.imageList[i]==''||this.imageList[i]==null||typeof(this.imageList[i])==undefined){
          this.imageList.splice(i,1);
          i=i-1;
        }
      }
    }
  }
};
</script>

<style scoped>
 span{
   color: #606266;
 }
</style>
