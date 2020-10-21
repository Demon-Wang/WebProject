<template>
  <div>
    <div style="height: 40px;padding-left: 100px;border-bottom: 3px #00ae66 solid;">
      <h2 style="color: #00ae66;cursor: pointer" @click="indexShow">天大房产</h2>
    </div>
    <div class="bg">
      <div style="width: 320px;margin: 0 auto;background: #fff;padding: 40px">
        <el-row v-show="loginShow">
          <el-row>
            <h2 style="color: #000">账号密码登录</h2>
          </el-row>
          <el-form autoComplete="on" :model="loginForm" :rules="loginRules" ref="loginForm">
            <el-form-item label="" prop="username">
              <el-input type="text" v-model="loginForm.username" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item label="" prop="password">
              <el-input type="password" v-model="loginForm.password" @keyup.enter.native="handleLogin"
                        placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="success" :loading="loading" @click.native.prevent="handleLogin" class="cbtn-bg">立即登录
              </el-button>
            </el-form-item>
          </el-form>
          <el-row style="line-height: 30px;color: #376699;font-size: 14px">
            <span style="cursor: pointer" @click="tabForm(2)">立即注册</span>
            <span style="float: right;cursor: pointer" @click="forget">忘记密码</span>
          </el-row>
          <el-row style="text-align: center;color: #999999;font-size: 12px;line-height: 50px">
            <span style="cursor: pointer">我已阅读并接受 《天大用户服务协议》及 《天大隐私政策》</span>
          </el-row>
        </el-row>
        <el-row v-show="!loginShow">
          <el-row>
            <h2 style="color: #000">欢迎注册</h2>
          </el-row>
          <el-form autoComplete="on" :model="loginForm" :rules="loginRules" ref="loginForm">
            <el-form-item label="" prop="username">
              <el-input type="text" v-model="loginForm.username" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item label="" prop="password">
              <el-input type="password" v-model="loginForm.password" @keyup.enter.native="handleRegis"
                        placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="success" :loading="loading" @click.native.prevent="handleRegis" class="cbtn-bg">立即注册
              </el-button>
            </el-form-item>
          </el-form>
          <el-row style="line-height: 30px;color: #376699;font-size: 14px">
            <span style="cursor: pointer" @click="tabForm(1)">已有账号？登录</span>
            <span style="float: right;cursor: pointer" @click="forget">忘记密码</span>
          </el-row>
          <el-row style="text-align: center;color: #999999;font-size: 12px;line-height: 50px">
            <span style="cursor: pointer">我已阅读并接受 《天大用户服务协议》及 《天大隐私政策》</span>
          </el-row>
        </el-row>
      </div>

    </div>
    <cfooter style="bottom: 0px"></cfooter>
  </div>
</template>
<script>
    import cfooter from "@/components/cfooter";

    export default {
        name: "login",
        components: {
            cfooter
        },
        data() {
            return {
                loginForm: {
                    username: '',
                    password: '',
                    type: 2
                },
                loginRules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 3, max: 11, message: '请输入长度至少为3位的用户名', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {min: 6, max: 12, message: '长度在 6 到 12 位的密码', trigger: 'blur'}
                    ]
                },
                loading: false,
                loginShow: true
            };
        },
        methods: {
            handleLogin() {
                this.$refs['loginForm'].validate( async (valid) => {
                    if (valid) {
                        const { data: res } = await this.$http.post('user/login', this.loginForm)
                        if (res.code == 1000) {
                            this.$message({
                                message: '登录成功',
                                type: 'success'
                            })
                            window.sessionStorage.setItem('token', res.data.token)
                            window.sessionStorage.setItem('name',res.data.user.username)
                            window.sessionStorage.setItem('id',res.data.user.id)
                            this.$router.push('/home')
                        } else {
                            this.$message({
                                message: res.data.message,
                                type: 'error'
                            })
                        }
                    } else {
                        return false
                    }
                })
            },
            handleRegis() {
                this.$refs['loginForm'].validate( async (valid) => {
                    if (valid) {
                        const { data: res } = await this.$http.post('user/addUser', this.loginForm)
                        if (res.code == 1000) {
                            this.$message({
                                message: '注册成功',
                                type: 'success'
                            })
                            this.$router.push('/login')
                        } else {
                            this.$message({
                                message: res.data.message,
                                type: 'error'
                            })
                        }
                    } else {
                        return false
                    }
                })
            },
            tabForm(id) {
                if (id === 1) {
                    this.loginShow = true
                    this.$refs.loginForm.resetFields()
                }
                if (id === 2) {
                    this.loginShow = false
                    this.$refs.loginForm.resetFields()
                }
            },
            forget() {
                alert("忘记密码");
            },
            indexShow() {
                this.$router.push({path: '/'});
            }
        }
    }
</script>

<style scoped>
  .bg {
    background-image: url("../assets/img/bg-login.jpg");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    height: 700px;
    color: #E9EEF3;
    padding-top: 150px;
  }

  .el-form-item__label {
    color: #000000;
  }

  .el-form-item__label {
    color: #E9EEF3;
  }

  .cbtn-bg {
    width: 100%;
    height: 40px;
    font-size: 14px;
    background: #00ae66;
    border: none;
    border-radius: 0px
  }
</style>
