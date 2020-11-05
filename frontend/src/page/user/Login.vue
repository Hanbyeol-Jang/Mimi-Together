<template>
  <div style="margin-top:6rem;" class="container">
    <div class="d-flex justify-content-center">
      <b-card
        style="font-family: 'Do Hyeon', sans-serif;width: 500px;"
        class="my-5"
      >
        <h3 style="font-family: 'Do Hyeon', sans-serif;">Login</h3>
        <div class="mt-4" v-if="page == 0">
          <b-card-text>
            <b-form-group label="아이디" label-for="input-email">
              <b-form-input
                id="password-live live-password"
                type="password"
                v-model="signupData.password"
                
              ></b-form-input>
            </b-form-group>

            <b-form-group label="비밀번호" label-for="input-password">
              <b-form-input
                id="password-live live-password"
                type="password"
                v-model="signupData.password"
                
              ></b-form-input>
            </b-form-group>
          </b-card-text>
        </div>
        <b-button @click="goMain()">로그인</b-button> <b-button @click="goJoin()">회원가입</b-button>
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
      goJoin(){
          this.$router.push({
            name: constants.URL_TYPE.USER.SIGNUP,
          });
      },
      goMain(){
          this.$router.push({
            name: constants.URL_TYPE.POST.MAIN,
          });
      }
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
