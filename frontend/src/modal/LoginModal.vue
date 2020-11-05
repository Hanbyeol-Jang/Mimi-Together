<template>
  <transition name="modal" appear>
    <div @keypress.enter="doLogin" class="modal modal-overlay" @click.self="$emit('close')">
      <div class="modal-window w-75" style="position:relative;max-width:500px;">
        <b-icon
          class="back"
          font-scale="1.4"
          style="position:absolute;top:25px;right:25px;z-index:12;cursor:pointer;"
          @click="$emit('close')"
          icon="x"
        ></b-icon>
        <p class="font-weight-bold h4 text-center my-4">LOGIN</p>
        <div class="modal-content pt-0 border-0">
          <hr class="m-0" />
          <div class="form__group field w-75 mx-auto mt-4">
            <input
              v-model="email"
              type="text"
              class="px-2 form__field"
              placeholder="Name"
              name="email"
              id="email"
              required
            />
            <label for="email" class="form__label">E-mail</label>
          </div>
          <div class="form__group field w-75 mx-auto my-4">
            <input
              v-model="password"
              type="password"
              class="px-2 form__field"
              placeholder="영문+숫자 8자 이상"
              name="name"
              id="name"
              required
            />
            <label for="name" class="form__label">Password</label>
          </div>
          <b-button
            style="border:2px solid orange;"
            variant="outline-warning"
            class="loginbtn m-3 w-25 mx-auto rounded-lg"
            @click="doLogin"
          >로그인</b-button>

          <hr class="my-2 mx-0" />
          <div class="my-3">
            <small style="font-family: 'IBMPlexSansKR-Text';color:gray;">비밀번호를 잊어버리셨나요?</small>
            <small
              v-b-modal.modal-1
              style="font-family: 'IBMPlexSansKR-Text';cursor:pointer;color:blue"
            >비밀번호 찾기</small>
          </div>
        </div>

        <b-modal id="modal-1" title="비밀번호 찾기" hide-footer>
          <div class="form__group1 field w-75 mx-auto mb-4">
            <input
              v-model="findpasswordData.nickname"
              type="text"
              class="px-2 form__field1"
              placeholder="nickname"
              name="name"
              id="name"
              required
            />
            <label for="name" class="w-100 form__label1">NickName(가입 시 작성한 닉네임을 입력해 주세요.)</label>
          </div>
          <div class="form__group1 field w-75 mx-auto my-4">
            <input
              v-model="findpasswordData.email"
              type="text"
              class="px-2 form__field1"
              placeholder="nickname"
              name="name"
              id="name"
              required
            />
            <label for="name" class="w-100 form__label1">E-mail(가입 시 작성한 이메일을 입력해주세요.)</label>
          </div>
          <div class="text-center my-3">
            <b-button
              style="border:2px solid orange;"
              variant="outline-warning"
              class="loginbtn w-25 rounded-lg"
              @click="handleOk"
            >SUBMIT</b-button>
          </div>
        </b-modal>
      </div>
    </div>
  </transition>
</template>

<script>
import constants from "../lib/constants";
import axios from "axios";
import "sweetalert2/dist/sweetalert2.min.css";
import swal from "sweetalert";

const SERVER_URL = constants.ServerUrl;
export default {
  methods: {
    doLogin() {
      axios
        .get(SERVER_URL + "/account/login", {
          params: {
            email: this.email,
            password: this.password,
          },
        })
        .then((res) => {
          console.log(res);
          this.token = res.data.object;
          if (res.status == 200) {
            this.$cookies.set("Auth-Token", this.token);
            this.$emit("close");
            this.$router.go();
          }
        })
        .catch((err) => {
          console.log(err);
          swal("아이디 및 비밀번호를 확인해주세요.", {
            buttons: false,
            timer: 1200,
          });
          this.email = "";
          this.password = "";
        });
    },
    handleOk() {
      axios
        .get(SERVER_URL + "/account/findPassword", {
          params: {
            email: this.findpasswordData.email,
            nickname: this.findpasswordData.nickname,
          },
        })
        .then((res) => {
          swal("비밀번호가 입력하신 메일주소로 전송되었습니다.", {
            buttons: false,
            timer: 1200,
          });
          this.$router.push("/");
        })
        .catch((err) => {
          console.log(err.response);
          this.findpasswordData.email = "";
          this.findpasswordData.nickname = "";
          swal("메일 및 닉네임을 다시 확인해주세요.", {
            buttons: false,
            timer: 1200,
          });
        });
    },
  },
  data: () => {
    return {
      constants,
      email: "",
      password: "",
      token: "",
      findpasswordData: {
        email: "",
        nickname: "",
      },
    };
  },
};
</script>


<style scoped>
* {
  font-family: "Do Hyeon", sans-serif;
}
.loginbtn {
  color: orange;
}
.loginbtn:hover {
  color: white;
  background-color: orange;
}
.back {
  color: gray;
}
.back:hover {
  color: black;
}
.modal-overlay {
  display: flex;
  align-items: center;
  justify-content: center;
  position: fixed;
  z-index: 30;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
}

.modal-window {
  background: #fff;
  border-radius: 4px;
  overflow: hidden;
}

.modal-content {
  padding: 10px 20px;
}

.modal-footer {
  background: #ccc;
  padding: 10px;
  text-align: right;
}

.form__group {
  position: relative;
  padding: 15px 0 0;
  /* margin-top: 10px; */
  width: 70%;
  text-align: left;
}
.form__field {
  width: 100%;
  border: 0;
  border-bottom: 1px solid gray;
  outline: 0;
  font-size: 0.8rem;
  padding: 7px 0;
  transition: border-color 0.2s;
  text-align: left;
}
.form__field::placeholder {
  color: transparent;
  text-align: left;
}
.form__field:placeholder-shown ~ .form__label {
  font-size: 0.8rem;
  cursor: text;
  top: 20px;
  text-align: left;
  left: 0;
}

.form__label {
  position: absolute;
  top: 0;
  left: 0;
  text-align: left;
  display: block;
  transition: 0.2s;
  font-size: 0.8rem;
  color: #9b9b9b;
}

.form__field:focus {
  padding-bottom: 6px;
  font-size: 0.8rem;
  text-align: left;
  border-image-slice: 1;
  border-bottom: 1px solid orange;
}
.form__field:focus ~ .form__label {
  position: absolute;
  top: 0;
  display: block;
  text-align: left;
  transition: 0.2s;
  font-size: 0.8rem;
  color: orange;
}
.form__group1 {
  position: relative;
  padding: 15px 0 0;
  /* margin-top: 10px; */
  width: 70%;
  text-align: left;
  font-family: "IBMPlexSansKR-Text";
}
.form__field1 {
  width: 100%;
  border: 0;
  border-bottom: 1px solid gray;
  outline: 0;
  font-size: 0.8rem;
  padding: 7px 0;
  transition: border-color 0.2s;
  text-align: left;
  font-family: "IBMPlexSansKR-Text";
}
.form__field1::placeholder {
  color: transparent;
  text-align: left;
  font-family: "IBMPlexSansKR-Text";
}
.form__field1:placeholder-shown ~ .form__label1 {
  font-size: 0.8rem;
  cursor: text;
  top: 20px;
  text-align: left;
  left: 0;
  font-family: "IBMPlexSansKR-Text";
}

.form__label1 {
  position: absolute;
  top: 0;
  left: 0;
  text-align: left;
  display: block;
  transition: 0.2s;
  font-size: 0.8rem;
  color: #9b9b9b;
  font-family: "IBMPlexSansKR-Text";
}

.form__field1:focus {
  padding-bottom: 6px;
  font-size: 0.8rem;
  text-align: left;
  border-image-slice: 1;
  border-bottom: 1px solid orange;
  font-family: "IBMPlexSansKR-Text";
}
.form__field1:focus ~ .form__label1 {
  position: absolute;
  top: 0;
  display: block;
  text-align: left;
  font-family: "IBMPlexSansKR-Text";
  transition: 0.2s;
  font-size: 0.8rem;
  color: orange;
}
</style>