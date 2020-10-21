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
            <el-col :span="12">
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
            <el-col :span="4" style="height: 100%; ">
              <el-button v-if="item.state==1"  type="primary" icon="el-icon-check" @click.stop="updateMsg(item)" size="mini"></el-button>
              <el-button v-else  type="danger" icon="el-icon-delete" @click.stop="deleteMsg(item.id)" size="mini"></el-button>
            </el-col>
          </el-row>
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
    return {
      searchContent: "",
      activeIndex: "1",
      horizontal:"1",
      images:"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4110839016,268976152&fm=26&gp=0.jpg",
      queryInfo: {
        query: "",
        pagenum: 1,
        pagesize: 10
      },
      editForm: {

      },
      MsgList: [],
      total: 0,
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
      let id = window.sessionStorage.getItem('id')
      const { data:res } = await this.$http.get(`demandMsg/getMsgsByUserId/${id}`, {
          params:{}
        });
      if(res.code !== 1000) return this.$message('获取信息失败')
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
    async updateMsg(dd) {
        this.editForm = dd
        this.editForm.state = 2
        const { data:res } = await this.$http.post('demandMsg/updateMsg',this.editForm)
        if (res.code !== 1000) {
          return this.$message.error(res.data);
        }
        this.$message.success('更新状态成功！')
        this.getMsgList()
    },
    deleteMsg(id) {
      this.$confirm("此操作将永久删除该租房信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(async () => {
          const { data:res } = await this.$http.post(`demandMsg/delMsgById/${id}`);
          if (res.code !== 1000) {
            this.$message.error(res.msg);
          } else {
            this.$message({
              type: "success",
              message: "删除成功!"
            });
            this.getMsgList();
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
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
