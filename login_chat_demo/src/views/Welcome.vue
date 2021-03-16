<template>
  <div id="welcomeUserDiv">
    <div>
    <!--      首页-->
      {{ $t('uiText.homePage') }}
    </div>
    <div>

    </div>
  </div>
</template>

<script>
export default {
  name: "Welcome",
  created() {
    console.log('welcome '+this.$route.name)
    this.$emit('fromRouterView',this.$route.name)
  }
}
</script>

<style scoped>

</style>