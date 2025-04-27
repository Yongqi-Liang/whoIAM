<template>
    <div>
        <div class="lamp g" v-if="status === 'green'"></div>
        <div class="lamp y" v-if="status === 'yellow'"></div>
        <div class="lamp r" v-if="status === 'red'"></div>
        <div class="lamp b" v-if="status === 'blue'"></div>
    </div>
</template>

<script>
export default {
    name: 'Lamp',
    props: {
        status: String,
    }
}
</script>

<style>
.lamp {
    width: 35px;
    height: 15px;
    border-radius: 5px;
    margin: 10px;
}
.g {
    background-color: rgb(113, 197, 149);    
}
.y {
    background-color: rgb(211, 211, 0);    
}
.r {
    background-color: rgb(195, 0, 0);    
}
.b {
    background-color: rgb(0, 0, 205);    
}
</style>