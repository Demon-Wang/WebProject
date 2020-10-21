<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>房屋管理</el-breadcrumb-item>
      <el-breadcrumb-item>添加出售信息</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>
      <el-alert
        title="添加出售信息"
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
        <el-step title="出售信息"></el-step>
        <el-step title="完成"></el-step>
      </el-steps>

      <el-form
        ref="addFormRef"
        :model="addForm"
        :rules="addFormRules"
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
              <el-input v-model="addForm.name"></el-input>
            </el-form-item>
            <el-form-item label="房屋面积(平方米)" prop="area">
              <el-input v-model="addForm.area" type="number"></el-input>
            </el-form-item>
            <el-form-item label="房屋户型(例如：X室X厅X卫)" prop="framework">
              <el-input v-model="addForm.framework"></el-input>
            </el-form-item>
          </el-tab-pane>

          <el-tab-pane label="详细信息" name="1">
            <el-form-item label="房屋朝向" prop="towards">
              <el-radio-group v-model="addForm.towards">
                  <el-radio :label="1">东</el-radio>
                  <el-radio :label="2">南</el-radio>
                  <el-radio :label="3">西</el-radio>
                  <el-radio :label="4">北</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="有无电梯" prop="elevator">
              <el-radio-group v-model="addForm.elevator">
                  <el-radio :label="0">无</el-radio>
                  <el-radio :label="1">有</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="所在楼层" prop="currentFloor">
              <el-input v-model="addForm.currentFloor" type="number"></el-input>
            </el-form-item>
            <el-form-item label="总楼层" prop="totalFloor">
              <el-input v-model="addForm.totalFloor" type="number"></el-input>
            </el-form-item>
            <el-form-item label="装修情况" prop="renovation">
              <el-radio-group v-model="addForm.renovation">
                  <el-radio :label="0">清水</el-radio>
                  <el-radio :label="1">简装</el-radio>
                  <el-radio :label="2">精装</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="主要家具家电" prop="furniture">
              <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10}" placeholder="请输入内容" maxlength="100" show-word-limit v-model="addForm.furniture"></el-input>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="户型图" name="2">
            <el-form-item label="户型图上传" prop="floorPlan">
              <el-upload
                action="http://49.232.29.150:8181/api/base64Upload"
                :headers="headerobj"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :on-success="handleSuccess"
                list-type="picture"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="出售信息" name="3">
            <el-form-item label="房屋产权" prop="property">
              <el-radio-group v-model="addForm.property">
                  <el-radio :label="1">公产</el-radio>
                  <el-radio :label="2">私产</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="售价(万元)" prop="price">
              <el-input v-model="addForm.price" type="number"></el-input>
            </el-form-item>
            <el-form-item label="房屋照片上传" prop="images">
              <el-upload
              action="http://49.232.29.150:8181/api/base64Upload"
              :headers="headerobj"
              :on-preview="handlePreview"
              :on-remove="handleRemove1"
              :on-success="handleSuccess1"
              list-type="picture"
            >
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
            </el-form-item>
            <el-form-item label="联系方式" prop="remarks">
              <el-input  placeholder="请输入联系方式" v-model="addForm.remarks"></el-input>
            </el-form-item>
            <el-form-item label="其他要求" prop="requirements">
              <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10}" placeholder="请输入内容" maxlength="100" show-word-limit v-model="addForm.requirements"></el-input>
            </el-form-item>
            <el-button type="primary" class="btnAdd" @click="add">添加出售信息</el-button>
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
      addForm: {
        type: 1,
        name: "",
        framework: "",
        area: 0,
        towards: 1,
        elevator: 0,
        currentFloor: 0,
        totalFloor: 0,
        renovation: 0,
        furniture: "",
        floorPlan:"",
        property: 1,
        price: 0,
        requirements: "",
        images: "",
        remarks: ""
      },
      addFormRules: {
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
        property: [
          { required: true, message: "请输入出售模式", trigger: "blur" }
        ],
        leaseTerm: [
          { required: true, message: "请输入租期", trigger: "blur" }
        ],
        price: [
          { required: true, message: "请输入价格", trigger: "blur" }
        ],
        towards: [
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
    beforTabLeave(activeName,oldActiveName){
        if(oldActiveName === '0' && (this.addForm.name === "" || this.addForm.area === 0 || this.addForm.framework === "")){
            this.$message.error('请先完成基本信息')
            return false
        }
        if(oldActiveName === '1' && (this.addForm.furniture === "" || this.addForm.totalFloor === 0 || this.addForm.currentFloor === 0 )){
            this.$message.error('请先完成详细信息')
            return false
        }
        if(oldActiveName === '2' && this.addForm.floorPlan === "" ){
            this.$message.error('请上传户型图')
            return false
        }
        return true
    },
    handlePreview(file) {
      this.previewPath = file.response.data
      this.previewVisible = true
    },
    handleRemove(fire) {
      const filePath =  fire.response.data + ';'
      this.addForm.floorPlan = this.addForm.floorPlan.replace(filePath,'')
    },
    handleSuccess(response) {
      this.addForm.floorPlan += response.data + ';'
    },
    handleRemove1(fire) {
      const filePath =  fire.response.data + ';'
      this.addForm.images = this.addForm.images.replace(filePath,'')
    },
    handleSuccess1(response) {
      this.addForm.images += response.data + ';'
    },
    add() {
      this.$refs.addFormRef.validate(async valid=>{
        if(!valid) {
          return this.$message.error('请先填写必填项！')
        }
        const { data:res } = await this.$http.post('house/addHouse',this.addForm)
        if (res.code !== 1000) {
          return this.$message.error(res.data);
        }
        this.$message.success('添加售房信息成功！')
        this.$router.push('/saleHouse')
      })
    }
  },
  created() {
  }
};
</script>

<style lang="less" scoped>

.previewImg{
  width: 100%;
}

.btnAdd{
  margin-top: 15px;
}
</style>
