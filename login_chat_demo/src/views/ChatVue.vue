<template>
  <div id="chatVue">
    <div class="sidebar">
      <card></card>
      <list></list>
    </div>
    <div class="main">
      <message></message>
      <usertext></usertext>
    </div>
  </div>
</template>

<script>
import card from '../components/chat/card.vue'
import list from '../components/chat/list.vue'
import message from '../components/chat/message.vue'
import usertext from '../components/chat/usertext.vue'
export default {
  name: "ChatVue",
  created() {
    console.log('ChatVue '+this.$route.name)
    this.$emit('fromRouterView',this.$route.name)

    //连接websocket
    this.$store.dispatch('connectChatWebsocket');
  },
  data () {
    return {

    }
  },
  mounted:function() {
    this.$store.dispatch('initData');
  },
  components:{
    card,
    list,
    message,
    usertext
  },
  methods: {

  }
}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
#chatVue {
  margin: 20px auto;
  width: 800px;
  height: 600px;
  overflow: hidden;
  border-radius: 10px;
}
.sidebar, .main {
  height: 100%;
}
.sidebar {
  float: left;
  color: #f4f4f4;
  background-color: #3bcb92;
  width: 200px;
}
.main {
  position: relative;
  overflow: hidden;
  background-color: #eee;
}

</style>