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
    <div style="width: 60%;margin: 0 auto;padding: 20px">
      <div style="padding-top: 10px">
          <el-row class="csearch" type="flex" justify="left">
            <el-col :span="14">
              <el-input
                placeholder="请输入内容"
                v-model="queryInfo.query"
                class="input"
                style="border-radius: 0px"
                clearable
                @clear="getMsgList"
                @keyup.enter.native="searchMsg"
              ></el-input>
            </el-col>
            <el-button type="success" class="cbtn-bg" @click="searchMsg">开始找信息</el-button>
          </el-row>
      </div>
    </div>
    <div style="width: 60%;margin: 0 auto;padding: 20px">
      <el-menu
        :default-active=activeIndex
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
      >
        <el-menu-item index="1">求租信息</el-menu-item>
        <el-menu-item index="2">求购信息</el-menu-item>
      </el-menu>
      <el-row>
        <h2>共找到{{total}}条信息</h2>
      </el-row>
      <el-divider></el-divider>
      <el-row style="width:100%">
        <el-col style="width:85%;float:left">
          <el-row
            :gutter="20"
            style="height: 200px;padding-top:20px;cursor:pointer;border-bottom: 1px #DCDFE6 solid"
            v-for="item in MsgList"
            :key="item.id">
            <el-col :span="4" style="height: 100%;">
              <el-row style="height: 90%;">
                <el-image :src=images style="height: 100%;"></el-image>
              </el-row>
            </el-col>
            <el-col :span="16">
              <el-row>
                <h2>{{item.userId}}</h2>
              </el-row>
              <el-row class="crow">
                <span>{{item.text}}</span>
              </el-row>
              <el-row class="crow">
                <span>联系电话：{{item.phone}}</span>
              </el-row>
            </el-col>
            <el-col :span="4" style="height: 100%;">
              <span v-if="item.share===2" style="color:red;font-weight: bold;font-size: 28px">已完成</span>
              <span v-else style="color:red;font-weight: bold;font-size: 28px">未完成</span>
              <br />
            </el-col>
          </el-row>
        </el-col>
        <el-col style="width:10%;float:right">
          <el-button @click="addDialogVisible = true" type="primary" class="cbtn">试试发布求助信息</el-button>
        </el-col>
      </el-row>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="queryInfo.pagesize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </div>
    <cfooter></cfooter>


    <el-dialog title="添加信息" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <el-form :model="addmsgForm" :rules="addmsgFormRules" ref="addmsgFormRef" label-width="90px" class="demo-ruleForm">
        <el-form-item label="信息类型" prop="type">
              <el-radio-group v-model="addmsgForm.type">
                  <el-radio :label="1">求租</el-radio>
                  <el-radio :label="2">求购</el-radio>
              </el-radio-group>
            </el-form-item>
        <el-form-item label="内容" prop="text">
          <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10}" v-model="addmsgForm.text"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="addmsgForm.phone"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addmsg">确 定</el-button>
      </span>
    </el-dialog>


  </div>
</template>

<script>
import cheader from "@/components/cheader";
import cfooter from "@/components/cfooter";
export default {
  components: {
    cheader: cheader,
    cfooter: cfooter
  },
  data() {
    // 自定义手机号校验规则
    var checkMobile = (rule, value, cb) => {
      const regMobile = /^[1][3,4,5,7,8][0-9]{9}$/;
      if (regMobile.test(value)) {
        return cb();
      }
      cb(new Error("请输入合法的手机号"));
    };
    return {
      searchContent: "",
      activeIndex: "1",
      horizontal:"1",
      Msgtype:1,
      images:"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4110839016,268976152&fm=26&gp=0.jpg",
      getUrl: "demandMsg/getAskBuyMsgs",
      queryInfo: {
        query: "",
        pagenum: 1,
        pagesize: 10
      },
      MsgList: [],
      total: 0,
      addDialogVisible: false,
      addmsgForm: {
        userId: "",
        phone: "",
        text: "",
        type: 1
      },
      addmsgFormRules: {
        type: [
          { required: true, message: "请选择信息种类", trigger: "blur" }
        ],
        text: [
          { required: true, message: "请输入信息内容", trigger: "blur" },
          {
            require: true,
            min: 10,
            message: "信息长度至少在 10 个字符以上",
            trigger: "blur"
          }
        ],
        phone: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          { validator: checkMobile, trigger: "blur" }
        ]
      },
    };
  },
  created() {
    this.getMsgList();
  },
  methods: {
    handleSelect(key, keyPath) {
      this.Msgtype = key
      this.getMsgList()
    },
    async getMsgList() {
      if(this.Msgtype == 1){
        this.getUrl= "demandMsg/getAskBuyMsgs"
      }
      else{
        this.getUrl= "demandMsg/getAskRentMsgs"
      }
      const { data:res } = await this.$http.get(this.getUrl, {
          params:{}
        });
      if(res.code !== 1000) return this.$message('获取信息失败')
      this.MsgList = res.data
      this.total = res.data.length
    },
    async searchMsg() {
      let text = this.queryInfo.query
      const { data:res } = await this.$http.get(`demandMsg/getMsgByTextLike/${text}`);
      if(res.code !== 1000) return this.$message('查询信息失败')
      this.MsgList = res.data
      this.total = res.data.length
    },
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize;
      this.getMsgList();
    },
    addDialogClosed() {
      this.$refs.addmsgFormRef.resetFields();
    },
    handleCurrentChange(newPage) {
      this.queryInfo.pagenum = newPage;
      this.getMsgList();
    },
    addmsg() {
      this.$refs.addmsgFormRef.validate(async valid => {
        if (!valid) return;
        if (window.sessionStorage.getItem('token') == null){
          this.$message.error("请登录");
          this.$router.push('/login')
          return ;
        }
        this.addmsgForm.userId = window.sessionStorage.getItem('id')
        const { data:res } = await this.$http.post("demandMsg/addMsg", this.addmsgForm);
        if (res.code === 1000) {
          this.$message.success("添加信息成功！");
        }
        else{
          this.$message.error("添加信息失败！");
        }
        this.addDialogVisible = false;
        this.getMsgList()
      });
    }
  }
};
</script>

<style scoped>
.cheader {
  width: 100%;
  height: 220px;
  background: #f5f5f6;
}
span {
  color: #000;
}
.csearch {
  width: 100%;
}
.cbtn-bg {
  background: #00ae66;
  border: none;
  border-radius: 0px;
}
.crow {
  line-height: 30px;
}
</style>
