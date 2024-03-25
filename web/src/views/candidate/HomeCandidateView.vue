<template>
  <el-menu router class="el-menu" active-text-color="#748199" mode="horizontal" :default-active="route.name">
    <div class="title-span" @click=" () => { router.push({name: 'exams'}) } ">
      <img src="@/assets/logo.png" style="height:30px; width:auto; border-radius: 50%;">  &nbsp;&nbsp;在线应试平台
    </div>
    <el-menu-item index="exams" class="el-menu-item">我的考试</el-menu-item>
    <el-menu-item index="score" class="el-menu-item">我的成绩</el-menu-item>
    <el-dropdown class="el-dropdown el-col-di7" trigger="click">
      <img class="el-avatar" size="30" :src="headImg" />
      <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item @click="()=>{ router.push({name: 'candidate_info'}) }">个人信息</el-dropdown-item>
        <el-dropdown-item @click="userStore.logout()" divided>退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </template>
    </el-dropdown>
  </el-menu>
  <el-card style="width: 1080px; height: 480px; margin:10px auto;"> <router-view /> </el-card>
</template>

<script lang="ts" setup>
import { useUserStore } from '@/store/user';
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import defaultHeadImg from '@/assets/defaultHeadImg.png';
const userStore = useUserStore();
const router = useRouter();
const route = useRoute();
const headImg = ref(defaultHeadImg);

userStore.getHeadImg((imgString)=>{
  const fileObj =  userStore.base64toFile(imgString, "file");
  const reader = new FileReader();
  reader.readAsDataURL(fileObj);
  reader.onload = (event) => {
    const fileContent = event.target?.result as string | ArrayBuffer | null;
    if(typeof fileContent === 'string')    headImg.value = fileContent as string;
  };
})

</script>

<style scoped>
.el-menu{
  background-color: #e0e8f6;
  height: 56px;
}
.title-span{
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 35px;
  font-size: large;
}
.title-span:hover{
  cursor: pointer;
}
.el-menu-item {
  font-size: large;
}
.el-menu-item.is-active{
  background-color: transparent !important;
}
.el-menu-item:hover{
  background-color: transparent !important;
  color: #e0e8f6;
}
.el-dropdown{
  height: 58px;
  position: absolute;
  right: 15%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.el-avatar{
    margin-top: 5px;
    margin-bottom: 5px;
    margin-right: 15px;
    border-radius: 50%;
}
.el-avatar:hover{
    cursor: pointer;
}
.el-col-di7{
    width:150px;
    border-radius: 10px;
    display: flex;
    justify-content: right;
    align-items: center;
    height: 55px;
    background-color: #fbfbfb;
}
</style>
