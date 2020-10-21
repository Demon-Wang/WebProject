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
        <h2>共有{{total}}套房子</h2>
      </el-row>
      <el-divider></el-divider>
      <el-row style="width:100%">
        <el-col style="width:85%;float:left">
          <el-row
            :gutter="20"
            style="height: 200px;padding-top:20px;cursor:pointer;border-bottom: 1px #DCDFE6 solid"
            v-for="item in houselist"
            :key="item.id"
          @click.native="info(item.id,item.type)">
            <el-col :span="4" style="height: 100%;">
              <el-row style="height: 90%;">
                <el-image :src="item.imagelist[0]" style="height: 100%;"></el-image>
              </el-row>
            </el-col>
            <el-col :span="12">
              <el-row>
                <h2>{{item.name}}</h2>
              </el-row>
              <el-row class="crow">
                <template>
                  <span v-if="item.elevator===1">公产|</span>
                  <span v-else >私产|</span>
                </template>
                <span>{{item.framework}}|</span>
                <span>{{item.area}}㎡|</span>
                <template>
                  <span v-if="item.elevator===1">有电梯|</span>
                  <span v-else >无电梯|</span>
                </template>
                <template >
                  <span v-show="item.type==2" v-if="item.share==1">合租|</span>
                  <span v-show="item.type==2" v-else >整租|</span>
                </template>
                <span v-show="item.type==2">{{item.leaseTerm}}个月起租|</span>
                <template>
                  <el-tag size="mini" v-if="item.state==2">已完成</el-tag>
                  <el-tag size="mini" v-else type="danger">未完成</el-tag>
                </template>
              </el-row>
            </el-col>
            <el-col :span="4" style="height: 100%;">
              <span style="color:red;font-weight: bold;font-size: 28px" v-show="item.type==2">{{item.price}}元/月</span>
              <span style="color:red;font-weight: bold;font-size: 28px" v-show="item.type==1">{{item.price}}万元</span>
              <br />
            </el-col>
            <el-col :span="4" style="height: 100%; ">
              <el-button v-if="item.state==1"  type="primary" icon="el-icon-check" @click.stop="updateHouse(item)" size="mini"></el-button>
              <el-button v-else  type="danger" icon="el-icon-delete" @click.stop="deleteHouse(item.id)" size="mini"></el-button>
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
      queryInfo: {
        query: "",
        pagenum: 1,
        pagesize: 10
      },
      editForm: {

      },
      houselist: [],
      total: 0
    };
  },
  created() {
    this.getHouseList();
  },
  methods: {
    handleSelect(key, keyPath) {
    },
    async getHouseList() {
      let id = window.sessionStorage.getItem('id')
      const { data:res } = await this.$http.get(`house/getHousesByUserId/${id}`, {
        params:{}
      });
      if(res.code !== 1000) return this.$message('获取房屋出租信息失败')
      this.houselist = res.data
      for(let a in this.houselist){
          this.houselist[a].imagelist = this.houselist[a].images.split(';')
      }
      this.total = res.data.length
    },
    info(id,type) {
        if(type == 1){
            this.$router.push({path:"house/SaleInfo",query:{ id: `${id}` }});
        }else{
            this.$router.push({path:"house/RentInfo",query:{ id: `${id}` }});
        }
    },
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize;
      this.getHouseList();
    },
    handleCurrentChange(newPage) {
      this.queryInfo.pagenum = newPage;
      this.getHouseList();
    },
    async updateHouse(dd) {
        this.editForm = dd
        this.editForm.state = 2
        const { data:res } = await this.$http.post('house/updateHouse',this.editForm)
        if (res.code !== 1000) {
          return this.$message.error(res.data);
        }
        this.$message.success('更新状态成功！')
        this.getHouseList()
    },
    deleteHouse(id) {
      this.$confirm("此操作将永久删除该租房信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(async () => {
          const { data:res } = await this.$http.post(`house/delHouseByHouseId/${id}`);
          if (res.code !== 1000) {
            this.$message.error(res.msg);
          } else {
            this.$message({
              type: "success",
              message: "删除成功!"
            });
            this.getHouseList();
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
