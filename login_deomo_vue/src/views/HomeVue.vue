<template>
  <div id="homeVue">
    <div id="headLink">
      <el-link type="primary" href="/managerLoginVue">管理员登录</el-link>&emsp;
      <el-link type="primary" href="/userLoginVue">用户登录</el-link>
    </div>
  </div>
</template>

<script>
export default {
name: "HomeVue"
}
</script>

<style scoped>
 #headLink{
   padding: 400px 0px 0px 400px;
 }
</style>