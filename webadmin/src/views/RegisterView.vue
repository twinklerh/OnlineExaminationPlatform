<template>
    <div>
        <el-row class="row">
            <el-col :span="2">
                <span class="el-input-text">用户名：</span>
            </el-col>
            <el-col :span="14">
                <el-input placeholder="用户名" v-model="username"/>
            </el-col>
        </el-row>
        <el-row class="row">
            <el-col :span="2">
                <span class="el-input-text">密码：</span>
            </el-col>
            <el-col :span="14">
                <el-input placeholder="密码" v-model="password" />
            </el-col>
        </el-row>
        <el-row class="row">
            <el-col :span="2">
                <span class="el-input-text">企业名称：</span>
            </el-col>
            <el-col :span="14">
                <el-input placeholder="企业名称" v-model="enterprise_name" />
            </el-col>
        </el-row>
        <el-row class="row">
            <el-col :span="2">
                <span class="el-input-text">邮箱：</span>
            </el-col>
            <el-col :span="14">
                <el-input placeholder="邮箱" v-model="email" />
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="4" :offset="16">
                <el-button type="primary" @click="submit">提交</el-button>
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts" setup>
import { useUserStore } from '@/store/user';
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { ref } from 'vue';

const username = ref('');
const password = ref('');
const email = ref('');
const enterprise_name = ref('');

function submit()   {
    console.log(useUserStore().token)
    axios.post('http://127.0.0.1:3000/root/submit/info/', {
        username: username.value,
        password: password.value,
        email: email.value,
        enterprise_name: enterprise_name.value
    }, {
        headers: {
            Authorization: 'Bearer ' + useUserStore().token,
        }
    }).then((response)=>{
        const data = response.data;
        if(data.error_message === 'success')    {
            ElMessage({type: 'success', message: '你成功添加了一位企业用户'});
            username.value = password.value = email.value = enterprise_name.value = "";
        }
    }).catch(()=>{ ElMessage.error("提交失败"); })
}


</script>

<style scoped>
.row {
    margin-bottom: 10px;
    text-align: right;  /* 右对齐 */
}
</style>