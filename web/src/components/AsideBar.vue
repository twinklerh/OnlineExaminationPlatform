<template>
  <el-col>
    <div class="text-div" @click="router.push('/enterprise/allproblems/')">在线应试平台</div>
    
    <el-divider class="el-divider"></el-divider>

    <el-menu router class="aside-bar" :default-active="route.name" active-text-color="#409eff"> 
      <el-menu-item index="allproblems" class="el-menu-item">
        <el-icon><Files /></el-icon>
        <span>试题库</span>
      </el-menu-item>

      <el-sub-menu index="2" class="el-sub-menu" >
        <template #title>
          <span style="font-size: 16px;">
            <el-icon><CirclePlus /></el-icon>
            <span>试题录入</span>
          </span>
        </template>
        <el-menu-item class="el-menu-item" index="addobjproblem">
          <el-icon><CirclePlus /></el-icon>
          <span>客观题</span>
        </el-menu-item>
        <el-menu-item class="el-menu-item" index="addsubproblem">
          <el-icon><CirclePlus /></el-icon>
          <span>主观题</span>
        </el-menu-item>
      </el-sub-menu>

      <el-menu-item class="el-menu-item" index="maketestpaper">
        <el-icon><Paperclip /></el-icon>
        <span>组卷</span>
      </el-menu-item>

      <el-sub-menu index="4" >
        <template #title>
          <span style="font-size: 16px;">
            <el-icon><Calendar /></el-icon>
            <span>考试中心</span>
          </span>
        </template>
        <el-menu-item index="addexam" class="el-menu-item">
          <el-icon><Calendar /></el-icon>
          <span>新建考试</span>
        </el-menu-item>
        <el-menu-item index="release" class="el-menu-item">
          <el-icon><Calendar /></el-icon>
          <span>发布考试</span>
        </el-menu-item>
      </el-sub-menu>

      <el-menu-item index="check" class="el-menu-item">
        <el-icon><Search /></el-icon>
        <span>阅卷</span>
      </el-menu-item>
      <el-menu-item index="grade" class="el-menu-item">
        <el-icon><Search /></el-icon>
        <span>成绩查询</span>
      </el-menu-item>
    </el-menu>
  </el-col>
</template>

<script lang="ts" setup>
import { Files, CirclePlus, Search, Calendar, Paperclip } from '@element-plus/icons-vue'
import { useRoute, useRouter } from 'vue-router';
const route = useRoute();
const router = useRouter();
</script>

<style scoped>
.aside-bar{
  background-color: #fbfbfb;
  border: 0;
}
.text-div{
  background-color: #fbfbfb;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 56px;
  cursor: pointer;
}
.el-divider{
  margin: 0;
  height: 0px;
}
.el-menu-item{
  font-size: 16px;
  height: 60px;
}
.el-menu-item:hover, .el-sub-menu:deep(.el-sub-menu__title):hover, .el-sub-menu:deep(.el-menu){
  background-color: transparent;
  color: #409eff;
}
</style>