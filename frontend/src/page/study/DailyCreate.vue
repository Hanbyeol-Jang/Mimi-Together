<template>
  <div style="margin-top:6rem;" class="mb-5 container">
    <div class="container">
      <img :src="images" class="logo w-25" alt />
      <b-row class="px-5">
        <b-col class="text-right">
          <b-button variant="info" size="sm m-0 py-0 px-1" v-b-modal.modal-1>
            <small class="mr-1">임시저장목록</small>
            <b-badge variant="light">{{tmpDailyData.length}}</b-badge>
          </b-button>
        </b-col>
        <b-col sm="12" class="px-1 mb-4">
          <small style="font-family:'Do Hyeon',sans-serif;" class="formtitle ml-3 float-left">제목</small>
          <b-form-input v-model="text" placeholder="제목" type="text"></b-form-input>
        </b-col>
        <b-col sm="12" class="px-1">
          <small style="font-family:'Do Hyeon',sans-serif;" class="formtitle ml-3 float-left">내용</small>
          <br />
          <editor
            class="p-0 text-left"
            :initialEditType="initialEditType()"
            ref="toastuiEditor"
            :options="editorOptions"
            :initialValue="editorText"
            style="padding:0.5rem;min-height:470px;"
          />
        </b-col>
      </b-row>
      <div class="p-3">
        <b-button
          class="bubut"
          variant="warning"
          @click="submitDaily(1)"
          style="border:1px solid orange;font-family:'Do Hyeon',sans-serif;"
        >작성</b-button>
        <b-button
          class="bubut ml-2"
          variant="info"
          @click="submitDaily(0)"
          style="font-family:'Do Hyeon',sans-serif;"
        >임시저장</b-button>
        <b-button
          class="ml-2"
          variant="outline-secondary"
          style="font-family:'Do Hyeon',sans-serif;"
          @click="goBack"
        >취소</b-button>
      </div>

      <b-modal id="modal-1" hide-footer title="임시저장 리스트">
        <div class="card mb-2" v-for="tmpdaily in tmpDailyData" :key="tmpdaily.did">
          <div class="px-3 py-2 card-body d-flex justify-content-between">
            <small class="my-auto">{{tmpdaily.title}}</small>
            <small class="my-auto">
              {{tmpdaily.posttime}}
              <b-button
                size="sm"
                class="ml-1"
                style="font-family:'Do Hyeon', sans-serif;"
                @click="continueWrite(tmpdaily.pid, tmpdaily.did)"
                variant="outline-success"
              >작성</b-button>
            </small>
          </div>
        </div>
      </b-modal>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import constants from "../../lib/constants";
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { optionsMixin } from "./mixin/option";
import { Editor } from "@toast-ui/vue-editor";
import codeSyntaxHighlight from "@toast-ui/editor-plugin-code-syntax-highlight";
import "highlight.js/styles/github.css";
import hljs from "highlight.js";
import "sweetalert2/dist/sweetalert2.min.css";
import swal from "sweetalert";

const SERVER_URL = constants.ServerUrl;
const baekjoon = "/백준";
const swea = "/swea";
const git = "/깃";
const initcontent =
  "# STUDY MATE \n 안녕하세요? **스터디메이트**입니다.\n저희는 **IT관련 공부**를 하고 계신 분들을 위해 MarkDown에디터를 제공하고 있습니다." +
  "\n \n *** \n \n### Code Block \n 사용하고 계신 언어의 코드블록은 다음과 같이 이용하실 수 있습니다. \n" +
  "\n```java" +
  "\n" +
  "System.out.println('스터디메이트');" +
  "\n" +
  "```" +
  "\n" +
  "```javascript" +
  "\nconsole.log('hi');" +
  "\n```" +
  "\n\n *** \n\n### 표\n" +
  "|                | 날짜                          |이름|" +
  "\n|----------------|-------------------------------|-----------------------------|" +
  "\n|1회|2019.01| 홍길동           |" +
  "\n \n *** \n \n### 추가 명령어\n 명령어를 통해 해당 문제/메인으로 이동할 수 있습니다. \n" +
  "/백준14891\n" +
  "/백준\n" +
  "/swea\n" +
  "/깃\n" +
  "\n *** \n\n";

export default {
  name: "DailyCreate",
  components: {
    editor: Editor,
  },
  data: () => {
    return {
      images: require("../../assets/img/logo.png"),
      editorHtml: "123",
      editorText: initcontent,
      initialEditType() {
        if (matchMedia("(max-width:480px)").matches) {
          return "wysiwyg";
        } else {
          return "markdown";
        }
      },
      editorOptions: {
        hideModeSwitch: false,
        placeholder: initcontent,
        initialValue: initcontent,
        initialEditType: "wysiwyg",
        previewStyle: "vertical",
        plugins: [[codeSyntaxHighlight, { hljs }]],

        extendedAutolinks: (content) => {
          //현재 문제 약 20000번 까지 있음
          //백준1800 입력하면 1800번으로 감
          for (let index = 0; index < 20000; index++) {
            var newone = baekjoon;
            newone += index;
            const matched = content.match(newone);
            if (content === newone) {
              return matched.map((m) => ({
                text: newone.substring(1, 3) + " " + newone.substring(3) + "번",
                url: "https://www.acmicpc.net/problem/" + index,
                range: [0, 8],
              }));
            }
            // /백준
            else if (content === baekjoon) {
              const matched = content.match(baekjoon);
              if (matched) {
                return matched.map((m) => ({
                  text: "백준",
                  url: "https://www.acmicpc.net",
                  range: [0, 3],
                }));
              }
            }
            // /SWEA
            else if (content === swea) {
              const matched = content.match(swea);
              if (matched) {
                return matched.map((m) => ({
                  text: "SWEA",
                  url: "https://swexpertacademy.com/main/main.do",
                  range: [0, 4],
                }));
              }
            } else if (content === git) {
              const matched = content.match(git);
              if (matched) {
                return matched.map((m) => ({
                  text: "깃허브",
                  url: "https://github.com/",
                  range: [0, 4],
                }));
              }
            }
          }
          return null;
        },
      },
      // editorText: "# This is Viewer.\n Hello World",
      profileInfo: [],
      tmpDailyData: [],
      text: "",
      content: "",
    };
  },
  created() {
    this.addprofileInfo();
  },
  methods: {
    addprofileInfo() {
      if (this.$cookies.isKey("Auth-Token")) {
        const token = this.$cookies.get("Auth-Token");
        axios
          .get(SERVER_URL + "/account/profile", {
            params: {
              Token: token,
            },
          })
          .then((res) => {
            this.profileInfo = res.data.object;
            this.getTmpDaily();
          })
          .catch((err) => {
            this.$router.push({
              name: constants.URL_TYPE.ERROR.ERRORPAGE,
              params: {
                code: err.response.data,
              },
            });
          });
      }
    },
    submitDaily(tmpN) {
      var date = new Date();
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var day = date.getDate();
      if (month < 10) {
        month = "0" + month;
      }
      if (day < 10) {
        day = "0" + day;
      }
      var today = year + "-" + month + "-" + day;

      if (this.text == "") {
        swal("제목을 입력해주세요", { buttons: false, timer: 1200 });
      } else if (
        this.$refs.toastuiEditor.invoke("getMarkdown") == "" ||
        this.$refs.toastuiEditor.invoke("getMarkdown") == initcontent
      ) {
        swal("내용을 입력해주세요", { buttons: false, timer: 1200 });
      } else {
        const dailydData = {
          title: this.text,
          body: this.$refs.toastuiEditor.invoke("getMarkdown"),
          // $refs를 통해 에디터를 찾고 invoke메소드로 다양한 기능을 호출할 수 있다.
          writer: this.profileInfo.nickname,
          pid: this.$route.params.post_id,
          uid: this.profileInfo.uid,
          tmp: tmpN,
          posttime: today
        };
        axios
          .post(SERVER_URL + "/diary/write", dailydData)
          .then((res) => {
            if (tmpN == 1) {
              this.$router.push({
                name: constants.URL_TYPE.STUDY.DAILYDETAIL,
                params: {
                  post_id: this.$route.params.post_id,
                  daily_id: res.data.object.did
                },
              });
            } else {
              this.$router.push({
                name: constants.URL_TYPE.STUDY.STUDYMAIN,
                params: { post_id: this.$route.params.post_id },
              });
            }
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    getTmpDaily() {
      axios
        .get(SERVER_URL + "/diary/list", {
          params: {
            pid: this.$route.params.post_id,
            tmp: 0,
          },
        })
        .then((res) => {
          const data = res.data.object[0];
          this.tmpDailyData = data.filter(
            (item) => item.uid == this.profileInfo.uid
          );
        })
        .catch((err) => {
          console.log(err);
        });
    },
    continueWrite(post_id, daily_id) {
      this.$router.push({
        name: constants.URL_TYPE.STUDY.DAILYUPDATE,
        params: {
          post_id: post_id,
          daily_id: daily_id,
        },
      });
    },
    goBack() {
      this.$router.push({
        name: constants.URL_TYPE.STUDY.STUDYMAIN,
        params: { post_id: this.$route.params.post_id },
      });
    },
  },
};
</script>

<style scoped>
.clickstudy {
  cursor: pointer;
}
</style>
