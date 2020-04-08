<template>
    <div class="search">
<!--        <h1>{{ msg }}</h1>-->
        <form>
            <input type="text" class="form-control" placeholder="search keyword" aria-describedby="basic-addon2">
            <span class="input-group-addon" id="basic-addon2"><button type="button" class="btn btn-sm">search</button></span>
        </form>
        <div id="my-component">

        </div>
    </div>
</template>

<script>
    export default {
        name: 'Search',
        props: {
            msg: String
        }
    }

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    h3 {
        margin: 40px 0 0;
    }
    ul {
        list-style-type: none;
        padding: 0;
    }
    li {
        display: inline-block;
        margin: 0 10px;
    }
    a {
        color: #42b983;
    }
</style>
