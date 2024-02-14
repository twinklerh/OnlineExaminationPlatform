<template>
    <div>
      <el-container class="el-container" >
        <el-aside class="el-aside">
            <div class="text-div">在线应试平台</div>
        </el-aside>
        <el-main>
          <div class="background">
              <div class="span-login">
                  <h2 class="h2-login">登录</h2>
                  <p class="p-login">&nbsp; &nbsp; 没有账号?
                      <RouterLink :to="{ name: 'register' }" class="router-link">注册</RouterLink>
                  </p>
                  <el-input class="el-input username" type="text" v-model="account_id" placeholder="用户名"/>
                  <el-input class="el-input password" type="password" v-model="password" placeholder="密码"/>
                  <span style="color:red; font-size: 13px;">&nbsp;{{ error_message }}</span>
                  <el-button type="primary" class="el-button-login" @click="login">登&nbsp;录</el-button>
              </div>
          </div>
        </el-main>
      </el-container>
    </div>
</template>

<script lang="ts" setup>
import { onBeforeMount, ref } from 'vue';
import { useUserStore } from '@/store/user';
import router from '@/router';

let account_id = ref('');
let password = ref('');
const error_message = ref('');

const userStore = useUserStore();

onBeforeMount(async()=>{
  userStore.token = localStorage.getItem('jwt_token') as string;
  await userStore.getUserInfo();
  if(userStore.token && userStore.status==='enterprise') router.push({name: 'enterprise'});
})

async function login(){
    error_message.value = await userStore.login(account_id.value, password.value);
    console.log(43, error_message.value, userStore.status);
    if(error_message.value != 'success')  return;
    userStore.token = localStorage.getItem('jwt_token') as string;
    if(userStore.token === '') return;
    if(userStore.status === 'enterprise') router.push({name: 'enterprise'})
}
</script>

<style scoped>
.text-div{
  margin-top: 27%;
  margin-left: 33%;
  font-size: 28px;
  letter-spacing: 7px;
  font-weight: lighter;
  color: lightcyan
}
.el-container{
  height: 800px;
  background-color: rgb(246,248,251);
}
.el-aside{
  width: 750px;
  background-color: rgb(0, 162, 255);
}
.background{
    background-color: rgb(246,248,251);
}
.span-login{
    margin-top: 10vh;
    margin-left: 90px;
    margin-right: 80px;
}
.h2-login{
    display: inline-block;
    font-weight: lighter;
}
.p-login{
    display: inline-block;
    font-weight: lighter;
    font-size: small;
}
.router-link{
    text-decoration: none;  /* 取消下划线 */
    color: blue;
}
.el-input {
    border: 1px;
    height: 50px;
    font-size: 15px;
    margin-bottom: 20px;
}
.password {
  margin-bottom: 2px;
}
.el-input :deep(.el-input__wrapper){
    padding: 1px 19px;
}
.password :deep(.el-input__inner){
    letter-spacing: 5px;
}
.el-button-login{
    width: 100%;
    height: 42px;
    margin-top: 10px;
    border-radius: 0.375rem;
}
</style>