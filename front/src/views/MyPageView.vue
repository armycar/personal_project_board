<template>
<b-navbar toggleable="lg" type="dark" variant="info">
    <b-navbar-brand href="/">Board</b-navbar-brand>

<b-navbar-nav>
        <a v-if="isLoggedIn" class="username">{{ username }}님 안녕하세요</a>
      </b-navbar-nav>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
    </b-collapse>
  </b-navbar>
  <br><br>
    <div class="container">
        <span style="font-size: 18px;">마이페이지</span>
        <br><br>
         <div class="image-wrapper">
                <input type="file" ref="fileInput" style="display:none" @change="handleFileChange">
                <img v-if="currentImageUrl" :src="currentImageUrl" class="profile-image" @click="openFileInput">
                <div v-else @click="openFileInput"  class="profile-placeholder"></div>
                <button class="photo-change" @click="changePhoto">변경</button>
              </div>
        <p><span class="highlight">닉네임</span>  {{nickname}}</p>
        <p> {{email}} </p>
        <div class="button-wrapper">
        <span><a href="/api/member/get/memberInfo" style="text-decoration: none;">나의활동</a></span>
        <span style="margin-left: 20px; margin-right: 20px;">|</span>    
        <span><a href="/api/member/change/memberInfo" style="text-decoration: none;">회원정보변경</a></span> 
        <span style="margin-left: 20px; margin-right: 20px;">|</span>
        <span @click="confirmLeaveMember" style="text-decoration: none;">회원탈퇴</span>
        </div>
    </div>
</template>

<script>
import member from '@/api/member';
import axios from 'axios';

export default {
    name: 'MyPageView',
    data() {
        return {
            url: '',
            nickname: '',
            email: '',
            seq: '',
            formData: new FormData(),
             currentImageUrl: ''
        };
    },
    computed: {
         isLoggedIn() {
      return !!sessionStorage.getItem('token');
    },
    username() {
      return sessionStorage.getItem('username');
    }
    },
    mounted() {
        this.seq = sessionStorage.getItem('token');
      
      if(this.seq) {
        member.getMember(this.seq)
        .then(response => {
            this.seq = response.data.seq;
            this.url = response.data.url;
            this.nickname = response.data.nickname;
            this.email = response.data.email;
            if(this.url) {
                this.currentImageUrl = `http://localhost:9244/api/download/img/member/${this.url}`;
            }
            else {
                this.currentImageUrl = '';
            }
        })
        .catch(error => {
            console.log(error);
            this.$message.error("로드 중 에러발생");
        });
    }
    else {
        console.log("토큰 값이 없습니다");
    }
    },
    methods: {
        openFileInput() {
            this.$refs.fileInput.click();
        },
        handleFileChange(event) {
            const file = event.target.files[0];
            this.formData = new FormData();
            this.formData.append("img", file);
            this.formData.append("seq", this.seq);

            const reader = new FileReader();
            reader.onload = (e) => {
                this.currentImageUrl = e.target.result;
            };
            reader.readAsDataURL(file);
        },
        changePhoto() {
            const file = this.formData.get('img');
        if (file) {
            axios
            .put(`http://localhost:9244/api/member/insert/photo`, this.formData, {
                headers: {
                "Content-Type": "multipart/form-data",
                },
            })
            .then((response) => {
                console.log(response);
                this.$router.push({ path: "/" });
                this.$message({
                message: "사진 등록이 완료되었습니다",
                type: "success",
                });
                if(response.data.url) {
                const imageUrl = `http://localhost:9244/api/download/img/member/${this.url}`;
                this.currentImageUrl = imageUrl;
                } else {
                    this.currentImageUrl = '';
                }
            })
            .catch((error) => {
                console.log(error);
                this.$message.error("사진 등록 중 에러가 발생했습니다");
            });
        } else {
            // 선택된 파일이 없는 경우에 대한 처리
            console.log("파일을 선택해주세요");
            this.$message.warning("파일을 선택해주세요");
        }
        },
        confirmLeaveMember() {
            if(window.confirm('정말로 탈퇴하시겠습니까?')) {
                this.leaveMember();
                this.$message({
                    message: '회원탈퇴가 완료되었습니다',
                    type: 'success'
                });
                this.$router.push('/');
                sessionStorage.removeItem('token');
            } else {
                this.$message({
                    message: '회원탈퇴가 취소되었습니다',
                    type: 'info'
                });
            }
        },
        leaveMember() {
            const seq = this.seq;
            member.leaveMember(seq)
            .then(response => {
                console.log(response);
            })
            .catch(error => {
                console.log(error);
                this.$message.error("회원 탈퇴 중 에러가 발생");
            });
        }
    }
};
</script>

<style scoped>
.container {
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 100vh;
}

.image-wrapper {
    position: relative;
    width: 100px;
    height: 100px;
    border-radius: 50%;
    overflow: hidden;
    border: 1px solid #000;
}

.profile-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.profile-placeholder {
    width: 100%;
    height: 100%;
    background-color: #fff;
}

.photo-change {
    position: absolute;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    padding: 4px 8px;
    background-color: #f2f2f2;
    border: none;
    border-radius: 4px;
    font-size: 12px;
    color: #333;
    cursor: pointer;
}

.highlight {
    font-weight: bold;
    font-size: 18px;
    
}
hr {
    border: none;
    border-top: 1px solid #bbb;
    margin: 20px 0;
    width: 50%;
}
.button-wrapper {
    font-size: 14px;
  cursor: pointer;
  margin-top: 8px;
  display: block;
  cursor: pointer;
}
.username {
  margin-right: 16px;
  padding: 8px 16px;
  font-size: 14px;
  text-decoration: none;
  cursor: pointer;
}
</style>