<template>
  <div style="margin-top:6rem;" class="container">
    <div class="container">
      <div class="card">
        <div class="p-4">
          <div class="d-flex">
            <h5 class="font-weight-bold text-left p-0 m-0">
              {{ dailyDetailData.title }}
            </h5>
            <div class="d-flex ml-auto">
              <div
                class="d-flex my-auto"
                v-if="profileInfo.uid === dailyDetailData.uid"
              >
                <button
                  style="font-family:'Do Hyeon',sans-serif;"
                  type="button"
                  @click="dailyUpdate(dailyDetailData.did)"
                  class="mr-2 btn btn-outline-primary btn-sm btn-rounded waves-effect"
                >
                  수정
                </button>
                <button
                  style="font-family:'Do Hyeon',sans-serif;"
                  type="button"
                  @click="dailyDelete(dailyDetailData.did)"
                  class="mr-2 btn btn-outline-danger btn-sm btn-rounded waves-effect"
                >
                  삭제
                </button>
              </div>
              <b-icon
                class="my-auto"
                @click="goStudyMain"
                icon="house-door"
              ></b-icon>
            </div>
          </div>
          <small class="float-left mt-1">
            {{ dailyDetailData.writer }},
            {{ dailyDetailData.posttime }}
          </small>
        </div>
        <hr class="m-0" />
        <Viewer
          class="p-2 text-left"
          style="min-height:58vh;"
          v-if="dailyDetailData.body != null"
          :initialValue="dailyDetailData.body"
          :options="viewerOptions"
        />
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import constants from "../../lib/constants";
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import codeSyntaxHighlight from "@toast-ui/editor-plugin-code-syntax-highlight";

import { Viewer } from "@toast-ui/vue-editor";

const SERVER_URL = constants.ServerUrl;
const baekjoon = "/백준";
const swea = "/swea";
const git = "/깃";

export default {
  name: "dailydetail",
  components: {
    Viewer,
  },
  data: () => {
    return {
      profileInfo: [],
      studyLists: [],
      dailyDetailData: [],
      viewerOptions: {
        plugins: [codeSyntaxHighlight],
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
    };
  },
  mounted() {
    this.detailData();
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
            this.addStudyList();
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
    goStudyMain() {
      this.$router.push({
        name: constants.URL_TYPE.STUDY.STUDYMAIN,
        params: {
          post_id: this.$route.params.post_id,
        },
      });
    },
    detailData() {
      axios
        .get(SERVER_URL + "/diary/details", {
          params: {
            did: this.$route.params.daily_id,
          },
        })
        .then((res) => {
          this.dailyDetailData = res.data.object;
        })
        .catch((err) => console.log(err.data));
    },
    dailyUpdate(daily_id) {
      this.$router.push({
        name: constants.URL_TYPE.STUDY.DAILYUPDATE,
        params: {
          post_id: this.$route.params.post_id,
          daily_id: daily_id,
        },
      });
    },
    dailyDelete(daily_id) {
      const deleteData = {
        did: this.$route.params.daily_id,
        uid: this.profileInfo.uid,
      };
      swal({
        text: "댓글을 삭제하시겠습니까?",
        dangerMode: true,
        buttons: true,
      }).then((willDelete) => {
        if (willDelete) {
          axios
            .post(SERVER_URL + "/diary/delete", deleteData)
            .then((res) => {
              this.$router.push({
                name: constants.URL_TYPE.STUDY.STUDYMAIN,
                params: {
                  post_id: this.$route.params.post_id,
                },
                a,
              });
            })
            .catch((err) => {
              console.log(err.data);
            });
        }
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
