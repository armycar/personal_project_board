<template>
<b-navbar toggleable="lg" type="dark" variant="success">
    <b-navbar-brand href="/">Board</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
    </b-collapse>
  </b-navbar>
  <br><br>
    <span style="font-size: 18px;">이메일로 회원가입</span>

    <br><br>
    <div>
        <form @submit.prevent="join">
          <el-row justify="center" align="middle">
    <el-col :span="4">
            <el-form-item style="width: 500px;">
          <el-row >
            <el-col :span="16">
              <el-input type="email" v-model="email" placeholder="이메일"></el-input>
            </el-col>
            <el-col :span="8">
            <el-button type="button" @click="dupchkEmail">중복확인</el-button>
            </el-col>
          </el-row>
            </el-form-item>
             </el-col>
  </el-row>
            <br>

<el-row justify="center" align="middle">
    <el-col :span="4">
            <el-form-item style="width: 500px;">
              <el-row>
                <el-col :span="16">
            <el-input type="text" id="nickname" v-model="nickname" placeholder="닉네임"></el-input>
                </el-col>
                <el-col :span="8">
            <el-button type="button" @click="dupchkName">중복확인</el-button>
                </el-col>
              </el-row>
            </el-form-item>
                   </el-col>
  </el-row>
            <br>

<el-row justify="center" align="middle">
    <el-col :span="4">
            <el-form-item style="width: 280px;">
            <el-input type="password" id="pwd" v-model="pwd" placeholder="비밀번호">
            </el-input>
            </el-form-item>
            </el-col>
  </el-row>
            <br>

<el-row justify="center" align="middle">
    <el-col :span="4">
            <el-form-item style="width: 280px;">
            <el-input type="password" id="pwdconfirm" v-model="pwdconfirm" placeholder="비밀번호 확인">
            </el-input>
            </el-form-item>
              </el-col>
  </el-row>
            <br>

            <el-button @click="join" >등록</el-button>
            </form>
        </div>


</template>

<script>
import apiMember from '@/api/member';

export default {
data() {
    return {
        email: '',
        pwd: '',
        pwdconfirm: '',
        nickname: '',
        emailcheck: false,
        namecheck: false
    }
},

methods: {
    join() {
      if(!this.emailcheck) {
        this.$message.warning('이메일 중복확인을 해주세요');
        return;
      }
      else if(!this.namecheck) {
        this.$message.warning('닉네임 중복확인을 해주세요');
        return;
      }
      else if(!this.email || !this.nickname || !this.pwd || !this.pwdconfirm) {
        this.$message.error("입력하지 않은곳이 있습니다");
        return;
      }
      else if(this.pwd!==this.pwdconfirm) {
        this.$message.error('비밀번호는 같아야 합니다');
        return;
      }
      else if(this.pwd.length<8) {
        this.$message.error('비밀번호는 8자리 이상이어야 합니다');
        return;
      }
    else {
      apiMember.joinMember(this.email, this.pwd, this.pwdconfirm, this.nickname)  
    .then(response => {
        console.log(response);
        this.$router.push('/');
        this.$message({
          message: '회원가입이 완료되었습니다',
          type: 'success'
        })
    
    })
    
  .catch((error) => {
          console.log(error);
          this.$message.error("회원가입 중 에러발생");
        });
    }
    },
    
  dupchkEmail() {
    apiMember.dupchkEmail(this.email)
    .then(response=> {
      if(response.data.count ==1) {
        this.$message.warning('이미 사용중인 이메일입니다');
      }
      else if(this.email=="") {
        this.$message.warning('사용 할 이메일을 입력해주세요');
      }
      else {
        this.$message.success('사용 가능한 이메일입니다');
        this.emailcheck = true;
      }
    })
    .catch(error =>{
      console.log(error);
      this.$message.error("중복확인 중 에러발생");
    }) 
  },
  dupchkName() {
    apiMember.dupchkName(this.nickname)
    .then(response=> {
      if(response.data.count ==1) {
        this.$message.warning('이미 사용중인 닉네임입니다');
      }
      else if(this.nickname=="") {
        this.$message.warning('사용 할 닉네임을 입력해주세요');
      }
      else {
        this.$message.success('사용 가능한 닉네임입니다');
        this.namecheck = true;
      }
    })
    .catch(error =>{
      console.log(error);
      this.$message.error("중복확인 중 에러발생");
    }) 
  }
  }
}

</script>

<style>
    
</style>
