<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>房屋管理</el-breadcrumb-item>
      <el-breadcrumb-item>修改出租信息</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>
      <el-alert
        title="修改出租信息"
        :closable="false"
        center
        show-icon
        type="info"
      >
      </el-alert>
      <el-steps
        :space="250"
        :active="activeIndex - 0"
        finish-status="success"
        align-center
      >
        <el-step title="基本信息"></el-step>
        <el-step title="详细信息"></el-step>
        <el-step title="户型图"></el-step>
        <el-step title="出租信息"></el-step>
        <el-step title="完成"></el-step>
      </el-steps>

      <el-form
        ref="editFormRef"
        :model="editForm"
        :rules="editFormRules"
        label-width="80px"
        label-position="top"
      >
        <el-tabs
          v-model="activeIndex"
          :before-leave="beforTabLeave"
          :tab-position="'left'"
        >
          <el-tab-pane label="基本信息" name="0">
            <el-form-item label="地址名称" prop="name">
              <el-input v-model="editForm.name"></el-input>
            </el-form-item>
            <el-form-item label="房屋面积(平方米)" prop="area">
              <el-input v-model="editForm.area" type="number"></el-input>
            </el-form-item>
            <el-form-item label="房屋户型(例如：X室X厅X卫)" prop="framework">
              <el-input v-model="editForm.framework"></el-input>
            </el-form-item>
          </el-tab-pane>

          <el-tab-pane label="详细信息" name="1">
            <el-form-item label="有无电梯" prop="elevator">
              <el-radio-group v-model="editForm.elevator">
                  <el-radio :label="0">无</el-radio>
                  <el-radio :label="1">有</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="所在楼层" prop="currentFloor">
              <el-input v-model="editForm.currentFloor" type="number"></el-input>
            </el-form-item>
            <el-form-item label="总楼层" prop="totalFloor">
              <el-input v-model="editForm.totalFloor" type="number"></el-input>
            </el-form-item>
            <el-form-item label="装修情况" prop="renovation">
              <el-radio-group v-model="editForm.renovation">
                  <el-radio :label="0">清水</el-radio>
                  <el-radio :label="1">简装</el-radio>
                  <el-radio :label="2">精装</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="主要家具家电" prop="furniture">
              <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10}" placeholder="请输入内容" maxlength="100" show-word-limit v-model="editForm.furniture"></el-input>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="户型图" name="2">
            <el-form-item label="户型图上传" prop="floorPlan">
              <el-upload
                action="http://49.232.29.150:8181/api/base64Upload"
                :headers="headerobj"
                :file-list="floorList"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :on-success="handleSuccess"
                list-type="picture"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="出租信息" name="3">
            <el-form-item label="出租模式" prop="share">
              <el-radio-group v-model="editForm.share">
                  <el-radio :label="0">整租</el-radio>
                  <el-radio :label="1">合租</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="租金/月" prop="price">
              <el-input v-model="editForm.price" type="number"></el-input>
            </el-form-item>
            <el-form-item label="最短租期/月" prop="leaseTerm">
              <el-input v-model="editForm.leaseTerm" type="number"></el-input>
            </el-form-item>
            <el-form-item label="房屋照片上传" prop="images">
              <el-upload
              action="http://49.232.29.150:8181/api/base64Upload"
              :headers="headerobj"
              :file-list="imageList"
              :on-preview="handlePreview"
              :on-remove="handleRemove1"
              :on-success="handleSuccess1"
              list-type="picture"
            >
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
            </el-form-item>
            <el-form-item label="联系方式" prop="remarks">
              <el-input  placeholder="请输入联系方式" v-model="editForm.remarks"></el-input>
            </el-form-item>
            <el-form-item label="其他要求" prop="requirements">
              <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10}" placeholder="请输入内容" maxlength="100" show-word-limit v-model="editForm.requirements"></el-input>
            </el-form-item>
            <el-button type="primary" class="btnedit" @click="edit">提交修改</el-button>
          </el-tab-pane>
        </el-tabs>
      </el-form>
    </el-card>

    <el-dialog
      title="图片预览"
      :visible.sync="previewVisible"
      width="50%">
      <img :src="previewPath" alt="" class="previewImg">
    </el-dialog>
  </div>
</template>

<script>
import _ from 'lodash'

export default {
  data() {
    return {
      activeIndex: "0",
      editForm: {
      },
      floorList:[
      ],
      imageList:[
      ],
      editFormRules: {
        name: [
          { required: true, message: "请输入地址名称", trigger: "blur" }
        ],
        framework: [
          { required: true, message: "请输入户型", trigger: "blur" }
        ],
        area: [
          { required: true, message: "请输入房屋面积", trigger: "blur" }
        ],
        elevator: [
          { required: true, message: "请选择", trigger: "blur" }
        ],
        currentFloor: [
          { required: true, message: "请输入房屋所在楼层", trigger: "blur" }
        ],
        totalFloor: [
          { required: true, message: "请输入总楼层数", trigger: "blur" }
        ],
        renovation: [
          { required: true, message: "请输入总楼层数", trigger: "blur" }
        ],
        furniture: [
          { required: true, message: "请输入家具家电情况", trigger: "blur" }
        ],
        floorPlan: [
          { required: true, message: "请上传户型图", trigger: "blur" }
        ],
        share: [
          { required: true, message: "请输入出租模式", trigger: "blur" }
        ],
        leaseTerm: [
          { required: true, message: "请输入租期", trigger: "blur" }
        ],
        price: [
          { required: true, message: "请输入价格", trigger: "blur" }
        ],
        images: [
          { required: true, message: "请上传房屋图片", trigger: "blur" }
        ],
        remarks: [
          { required: true, message: "请输入联系方式", trigger: "blur" }
        ]
      },
      headerobj:{
        token:window.sessionStorage.getItem('token')
      },
      previewPath:'',
      previewVisible:false
    };
  },
  methods: {
    async getHouseById(id){
      const { data:res } = await this.$http.get(`house/getHouseByHouseId/${id}`);
      if(res.code !== 1000) return this.$message('查询房屋信息失败')
      this.editForm = res.data
      let floor = this.editForm.floorPlan.split(';')
      for(let i=0;i<floor.length-1;i++){
          let temp = {}
          temp['name'] = floor[i].substring(floor[i].length-10,floor[i].length)
          temp['url'] =floor[i]
          this.floorList.push(temp)
      }
      let image = this.editForm.images.split(';')
      for(let i=0;i<image.length-1;i++){
          let temp = {}
          temp['name'] = image[i].substring(image[i].length-10,image[i].length)
          temp['url'] =image[i]
          this.imageList.push(temp)
      }
    },
    beforTabLeave(activeName,oldActiveName){
      if(oldActiveName === '0' && (this.editForm.name === "" || this.editForm.area === 0 || this.editForm.framework === "")){
            this.$message.error('请先完成基本信息')
            return false
      }
      if(oldActiveName === '1' && (this.editForm.furniture === "" || this.editForm.totalFloor === 0 || this.editForm.currentFloor === 0 )){
        this.$message.error('请先完成详细信息')
        return false
      }
      if(oldActiveName === '2' && this.editForm.floorPlan === "" ){
        this.$message.error('请上传户型图')
        return false
      }
      return true
    },
    handlePreview(file) {
      if(file.response == null)
        this.previewPath = file.url
      else
        this.previewPath = file.response.data
      this.previewVisible = true
    },
    handleRemove(fire) {
      let filePath = ''
      if(fire.response == null)
        filePath = fire.url+ ';'
      else
        filePath =  fire.response.data + ';'
      this.editForm.floorPlan = this.editForm.floorPlan.replace(filePath,'')
    },
    handleSuccess(response) {
      this.editForm.floorPlan += response.data + ';'
    },
    handleRemove1(fire) {
      let filePath = ''
      if(fire.response == null)
        filePath = fire.url+ ';'
      else
        filePath =  fire.response.data + ';'
      this.editForm.images = this.editForm.images.replace(filePath,'')
    },
    handleSuccess1(response) {
      this.editForm.images += response.data + ';'
    },
    edit() {
      this.$refs.editFormRef.validate(async valid=>{
        if(!valid) {
          return this.$message.error('请先填写必填项！')
        }
        const { data:res } = await this.$http.post('house/updateHouse',this.editForm)
        if (res.code !== 1000) {
          return this.$message.error(res.data);
        }
        this.$message.success('修改租房信息成功！')
        this.$router.push('/rentHouse')
      })
    }
  },
  created() {
      var id = this.$route.query.id
      this.getHouseById(id);
  }
};
</script>

<style lang="less" scoped>

.previewImg{
  width: 100%;
}

.btnedit{
  margin-top: 15px;
}
</style>
