<template>
    <el-card class="card">
        <el-input v-model="username" placeholder="用户名" />
        <div style="width: 40px; height:20px;"></div>
        <el-input v-model="password" type="password" placeholder="密码" />
        <div style="height:20px; color:red;font-size: 12px;;">{{ error_message }}</div>
        <el-button type="primary" style="width: 100%;" @click="login">登录</el-button>
    </el-card>
</template>

<script lang="ts" setup>
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
const error_message = ref('');
const username = ref('');
const password = ref('');
const router = useRouter();
function login()    {
    if(username.value === '' || password.value ==='')   return;
    axios.post('http://127.0.0.1:3000/root/account/token/', {
        account_id: username.value,
        password: password.value,
    }).then((resp)=>{
        const result = resp.data;
        error_message.value = result.error_message;
        if(result.error_message === 'success')  {
            localStorage.setItem("jwt_token", result.token);
            router.push({name: "register"});
        }
    }).catch(()=>{
        ElMessage.error("登录失败");
    })
}

</script>

<style scoped>
.card {
    width: 300px;
    height: 220px;
    margin: 100px auto;
}
</style>