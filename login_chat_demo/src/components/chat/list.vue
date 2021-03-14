<template>
  <div id="list">
    <ul v-show="isManager">
      <li v-for="(userItem,userIndex) in dbUsers" :class="{ active: userCurrentObj? userCurrentObj.userAccount===userItem.userAccount:false }"
          v-on:click="changeUserCurrentObject(userItem)"><!--   :class="[item.id === currentSessionId ? 'active':'']" -->
        <img class="avatar" :src="getTheImgUrl(userIndex)" :alt="userItem.userAccount">
        <el-badge :is-dot="needDot[currentUsername+'#'+userItem.userAccount]">
          <p class="name">{{userItem.userAccount}}</p>
        </el-badge>
      </li>
    </ul>
  	<ul>
  		<li v-for="(managerItem,managerIndex) in dbManagers" :class="{ active: managerCurrentObj? managerCurrentObj.managerAccount === managerItem.managerAccount:false }"
          v-on:click="changeManagerCurrentObject(managerItem)"><!--   :class="[item.id === currentSessionId ? 'active':'']" -->
  			<img class="avatar" :src="getTheImgUrl(managerIndex)" :alt="managerItem.managerAccount">
        <el-badge :is-dot="needDot[currentUsername+'#'+managerItem.managerAccount]">
          <p class="name">{{managerItem.managerAccount}}</p>
        </el-badge>
  		</li>
  	</ul>
  </div>
</template>

<script>
import {mapState} from 'vuex'
import Vue from "vue";

export default {
  name: 'list',
  created() {
    if(window.sessionStorage.getItem('loginRole') === 'roleManager'){
      this.isManager = true;
    }
  },
  data () {
    return {
      isManager: false,
      currentUsername: window.sessionStorage.getItem('username'),
    }
  },
  computed: mapState([
    //'sessions',
    'dbUsers',
    'dbManagers',
    'currentSessionId',
    'userCurrentObj',
    'managerCurrentObj',
    'needDot'
	]),
  methods:{
  	changeCurrentSessionId:function (id) {
  		this.$store.commit('changeCurrentSessionId',id)
  	},
    changeUserCurrentObject:function (obj) {
  		this.$store.commit('changeUserCurrentObject',obj)
  	},
    changeManagerCurrentObject:function (obj) {
  		this.$store.commit('changeManagerCurrentObject',obj)
  	},
    getTheImgUrl: function (index){
  	  let imgurl = this.$store.getters.getImgUrl(index);
      console.log(index);
      console.log(imgurl);
  	  return imgurl;
    }
  }
}
</script>

<style lang="scss" scoped>
#list {
  ul{
    padding-left: 0px;
  }
	li {
		padding: 15px 15px;
		border-bottom: 1px solid #292C33;
		cursor: pointer;
		&:hover {
			background-color: rgba(255, 255, 255, 0.03);
		}
	}
  li.active {/*注意这个是.不是冒号:*/
			background-color: rgba(255, 255, 255, 0.1);
	}
	.avatar {
		border-radius: 2px;
		width: 30px;
		height: 30px;
		vertical-align: middle;
	}
	.name {
		display: inline-block;
		margin-left: 15px;
    margin-top: 0px;
	}
}
</style>
