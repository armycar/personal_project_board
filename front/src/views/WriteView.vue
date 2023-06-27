<template>
  <el-row>
    <el-col :span="2"></el-col>
    <el-col :span="20">
      <el-card>
        <div>
          <router-link :to="{ path: '/' }">
            <el-button type="text" class="left-button">Back</el-button>
          </router-link>

          <span>게시물 올리기</span>

          <el-button @click="writeArticle" type="text" class="right-button">등록</el-button>
        </div>
        <br /><br />

        <div>
          <el-form
            :label-position="right"
            label-width="100px"
            :model="formData"
          >
            <el-form-item label="제목">
              <el-input
                type="text"
                v-model="formData.title"
                placeholder="제목을 입력하세요"
              ></el-input>
            </el-form-item>
            <el-form-item label="본문">
              <el-input
                type="textarea"
                v-model="formData.detail"
                placeholder="본문을 입력하세요"
                rows="10"
              ></el-input>
            </el-form-item>
            <el-form-item label="이미지추가">
                <input type="file" @change="handleFileUpload">
            </el-form-item>
          </el-form>
        </div>
      </el-card>
    </el-col>
    <el-col :span="2"></el-col>
  </el-row>
</template>

<script>
import axios from 'axios'; // axios import 추가

export default {
  props: {
    seq: {
      type: Number,
      required: true,
      default: 0
    },
    cateSeq: {
      type: Number,
      required: true,
      default: 1
    }
  },

  data() {
    return {
      formData: {
        title: "",
        detail: ""
      },
      seqValue: null,
    };
  },

  mounted() {
    this.seqValue = sessionStorage.getItem('token');
    if (!this.seqValue) {
      this.$message.warning('로그인이 필요합니다');
      this.$router.push('/');
    }
  },

  methods: {
    handleFileUpload(event) {
        const file = event.target.files[0];
        this.formData.img = file;
    },
     
    writeArticle() {
      if (!this.formData.title || !this.formData.detail) {
        this.$message.error("제목과 본문을 작성해주세요");
        return;
      }

      const formData = new FormData();
      formData.append("title", this.formData.title);
      formData.append("detail", this.formData.detail);
      
      if(this.formData.img) {
      formData.append("img", this.formData.img);
      }

      axios
        .put(`http://localhost:9244/api/article/write?seq=${this.seqValue}&cateSeq=${this.cateSeq}`, formData, { // seq와 cateSeq에 props의 값 대입
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((response) => {
          console.log(response);
          this.$router.push({ path: '/' });
          this.$message({
          message: '게시글이 등록 되었습니다',
          type: 'success'
        })
        })
        .catch((error) => {
          console.log(error);
          this.$message.error("게시물 작성중 에러발생");
        });
    },
  },
};
</script>

<style scoped>
.left-button {
    float: left;
    padding: 3px 0;
}
.right-button {
    float: right;
    padding: 3px 0;
}
</style>