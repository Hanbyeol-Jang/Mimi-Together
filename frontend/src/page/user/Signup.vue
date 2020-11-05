<template>
  <div style="margin-top:6rem;" class="container">
    <div class="d-flex justify-content-center">
      <b-card
        style="font-family: 'Do Hyeon', sans-serif;width: 500px;"
        class="my-5"
      >
        <h3 style="font-family: 'Do Hyeon', sans-serif;">JOIN</h3>
        <div class="mt-4" v-if="page == 0">
          <b-card-text>
            <b-form-group label="아이디" label-for="input-email">
             <b-form-input
                id="ID"
              ></b-form-input>
            </b-form-group>

            <b-form-group label="비밀번호" label-for="input-password">
              <b-form-input
                id="password-live live-password"
                type="password"
                v-model="signupData.password"
                
                :state="passwordState"
                aria-describedby="password-live-feedback password-live-help"
                trim
              ></b-form-input>
              <b-form-invalid-feedback  id="password-live-feedback"
                >영어 대소문자, 숫자, 특수기호(!,@,#,$,%,^,&,*,(,))를 포함하여 8자 이상을
                입력해주세요.</b-form-invalid-feedback
              >
              
            </b-form-group>

            <b-form-group 
              label="비밀번호 확인"
              label-for="input-passwordConfirm"
            >
              <b-form-input
                type="password"
                v-model="signupData.passwordConfirm"
                id="input-passwordConfirm passwordconfirm-live"
                :state="passwordconfirmState"
                aria-describedby="passwordconfirm-live-feedback passwordconfirm-live-help"
                trim
              ></b-form-input>
              <b-form-invalid-feedback id="passwordconfirm-live-feedback"
                >비밀번호와 틀립니다.</b-form-invalid-feedback
              >
            </b-form-group>
          </b-card-text>
        </div>
        <div class="mt-4" v-if="page == 1">
          <b-card-text>
            <b-form-group label="닉네임" label-for="input-nickname">
              <b-card class="baseInput" no-body id="input-nickname">{{
                signupData.nickname
              }}</b-card>
            </b-form-group>
            <b-form-group  label="자기소개" label-for="input-intro">
              <b-form-textarea
                id="input-intro"
                v-model="signupData.intro"
                rows="3"
                max-rows="6"
              ></b-form-textarea>
            </b-form-group>
          </b-card-text>
        </div>
        <div class="mt-4" v-if="page == 2">
          <b-card-text>
            <div v-if="!signupData.profile_image">
              <p style="font-family: 'Do Hyeon', sans-serif;color:orange;">
                Profile Image
              </p>
              <b-form-file
                @change="onChangeImages"
                v-model="file"
                ref="file-input"
                class="w-75 mx-auto mb-3"
              ></b-form-file>
            </div>
            <div v-else>
              <img :src="signupData.profile_image" style="width: 100%;" />
              <b-icon
                class="my-3"
                @click="removeImage"
                font-scale="1.5"
                icon="trash"
                variant="danger"
              ></b-icon>
            </div>
          </b-card-text>
        </div>

        <div class="d-flex justify-content-between">
          <b-button v-if="0 < page && page < 3" @click="pageSwitch(-1)" pill
            >이전</b-button
          >
          <b-button
            v-if="page < 2"
            @click="pageSwitch(1)"
            pill
            variant="primary"
            >다음</b-button
          >
          <b-button v-if="page == 2" @click="doSign" pill variant="success"
            >완료</b-button
          >
        </div>
      </b-card>
    </div>
  </div>
</template>

<script>
import constants from "../../lib/constants";
import axios from "axios";
import "sweetalert2/dist/sweetalert2.min.css";
import swal from "sweetalert";

const SERVER_URL = constants.ServerUrl;

export default {
  name: "Signup",
  computed: {
    passwordState() {
      var special = ["!", "@", "/", "#", "$", "%", "^", "&", "*", "(", ")"]
      var eng = ["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
      "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"]
      var digit = ["1","2","3","4","5","6","7","8","9","0"]

      var isSpecial = false;
      var isEng = false;
      var isDigit = false;

    for (let index = 0; index < this.signupData.password.length; index++) {
        if(special.indexOf(this.signupData.password[index]) != -1){
          isSpecial= true;
        }
        if(eng.indexOf(this.signupData.password[index]) != -1){
          isEng= true;
        }
        if(digit.indexOf(this.signupData.password[index]) != -1){
          isDigit= true;
        }
      }
      
    console.log(isSpecial)
    console.log(isEng)
    console.log(isDigit)
    console.log(this.signupData.password.length)

      if(isSpecial == true&& isEng == true && isDigit == true && this.signupData.password.length > 7){
        return true;
      }else{
        return false;
      }
    },
    passwordconfirmState() {
      return this.signupData.password === this.signupData.passwordConfirm
        ? true
        : false;
    },
  },
  data: () => {
    return {
      page: 0,
      signupData: {
        email: "",
        nickname: "",
        password: "",
        passwordConfirm: "",
        intro: "",
        profile_image: "",
      },
    };
  },
  methods: {
    pageSwitch(n) {
      this.page = this.page + n;
    },
    onChangeImages(e) {
      const selectedImage = e.target.files[0];
      this.createBase64Image(selectedImage);
    },
    createBase64Image(fileObject) {
      this.signupData.profile_image = new Image();
      const reader = new FileReader();
      reader.onload = (e) => {
        this.signupData.profile_image = e.target.result;
      };
      reader.readAsDataURL(fileObject);
    },
    removeImage: function(e) {
      this.signupData.profile_image = "";
    },
    doSign() {
      if (this.signupData.password == null) {
        this.$swal(
          "가입 실패",
          "비밀번호가 입력되지 않았습니다. 확인해주세요.",
          "error"
        );
      } else if (this.signupData.passwordConfirm == null) {
        this.$swal(
          "가입 실패",
          "비밀번호확인이 입력되지 않았습니다. 확인해주세요.",
          "error"
        );
        alert("비밀번호확인이 입력되지 않았습니다. 확인해주세요.");
      } else if (this.signupData.password != this.signupData.passwordConfirm) {
        this.$swal(
          "가입 실패",
          "비밀번호가 확인값과 틀립니다. 확인해주세요.",
          "error"
        );
      } else {
        axios
          .post(SERVER_URL + "/account/signup", this.signupData)
          .then((res) => {
            swal("회원가입되었습니다.", { buttons: false, timer: 1200 });
            this.$router.push("/");
          })
          .catch((err) => {
            console.log(err.response);
          });
      }
    },
  },
};
</script>

<style scoped>
.baseInput {
  text-align: left;
  height: 38px;
  padding: 5px;
  padding-left: 15px;
}
</style>
