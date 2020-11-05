<template>
  <div style="margin-top:6rem;" class="container">
    <div class="row">
      <b-card class="m-1 col-12 col-md-5">
        <p>이미 계정이 있으신 경우</p>
        <hr>
        <small>이미 가입한 계정이 있으신 경우 회원가입을 하실수 없습니다.<br>
          <b-button @click="goMainpage" variant="light">메인페이지</b-button>로 가셔서 로그인을 시도해주세요.</small>
      </b-card>
      <b-card class="m-1 col-12 col-md-6">
        <p>계정이 없는데 회원가입이 안되는 경우</p>
        <hr>
        <small>1. 회원가입시 이메일을 선택 안했을때</small><br/>
        <small>2. 다른 계정으로 회원가입을 원할때</small>
        <hr>
        <div>
          <b-button id="show-btn" variant="outline-dark" @click="$bvModal.show('bv-modal-example')">카카오 연동 해지하는 방법</b-button>
          <b-modal id="bv-modal-example" hide-footer>
            <template v-slot:modal-title>카카오 연동 해지하는 방법</template>
            <div class="d-block text-center">
              <b-button variant="outline-dark" href="https://accounts.kakao.com/weblogin/account/partner" target="_blank">카카오계정 페이지로 이동</b-button>
              <br>
              <img src="../../../src/assets/img/quitkakao/2.png">
              <img src="../../../src/assets/img/quitkakao/3.png">
              <img src="../../../src/assets/img/quitkakao/1.png">
            </div>
          </b-modal>
        </div>

      </b-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "Duplicate",
  methods: {
    goMainpage() {
      this.$router.push('/')
    }
  }
}
</script>

<style scoped>
img {
  width: 100%;
}
</style>