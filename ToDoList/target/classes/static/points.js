var points = [];

function findPoint (pointId) {
  return points[findPointKey(pointId)];
}

function findPointKey (pointId) {
  for (var key = 0; key < points.length; key++) {
    if (points[key].id == pointId) {
      return key;
    }
  }
}

var pointService = {
  findAll(fn) {
    axios
      .get('/api/v1/points')
      .then(response => fn(response))
      .catch(error => console.log(error))
  },

  findById(id, fn) {
    axios
      .get('/api/v1/points/' + id)
      .then(response => fn(response))
      .catch(error => console.log(error))
  },

  create(point, fn) {
    axios
      .post('/api/v1/points', point)
      .then(response => fn(response))
      .catch(error => console.log(error))
  },

  update(id, point, fn) {
    axios
      .put('/api/v1/points/' + id, point)
      .then(response => fn(response))
      .catch(error => console.log(error))
  },

  deletePoint(id, fn) {
    axios
      .delete('/api/v1/points/' + id)
      .then(response => fn(response))
      .catch(error => console.log(error))
  },

  deletePoints(points, fn){
  axios
  .delete('/api/v1/points')
  .then(response=> fn(response))
  .catch(error => console.log(error))
  }
}

var List = Vue.extend({
  props: ['points'],
  template: '#point-list',
  data: function () {
  return {points: [], searchKey: ''};
  },
  computed: {
    filteredPoints() {
      return this.points.filter((point) => {
      	return point.name.indexOf(this.searchKey) > -1
      	  || point.description.indexOf(this.searchKey) > -1
      })
    }
  },
  mounted() {
    pointService.findAll(r => {this.points = r.data; points = r.data})
  }
});

var Point = Vue.extend({
  template: '#point',
  data: function () {
    return {point: findPoint(this.$route.params.point_id)};
  }
});

var PointEdit = Vue.extend({
  template: '#point-edit',
  data: function () {
    return {point: findPoint(this.$route.params.point_id)};
  },
  methods: {
    updatePoint: function () {
      pointService.update(this.point.id, this.point, r => router.push('/'))
    }
  }
});

var PointDelete = Vue.extend({
  template: '#point-delete',
  data: function () {
    return {point: findPoint(this.$route.params.point_id)};


  },
  methods: {
    deletePoint: function () {
      pointService.deletePoint(this.point.id, r => router.push('/'));

      }
  }

  });


var ClearList = Vue.extend({
  template: '#delete-all',
  data: function () {
  return points;
  },
  methods: {
      deleteAll: function () {
        pointService.deletePoints(this.points, r => router.push('/'));
        }
    }
    });


var AddPoint = Vue.extend({
  template: '#add-point',
  data() {
    return {
      point: {name: '', description: ''}
    }
  },
  methods: {
    createPoint() {
      pointService.create(this.point, r => router.push('/'))
    }
  }
});

var router = new VueRouter({
	routes: [
		{path: '/', component: List},
		{path: '/point/:point_id', component: Point, name: 'point'},
		{path: '/add-point', component: AddPoint},
		{path: '/point/:point_id/edit', component: PointEdit, name: 'point-edit'},
		{path: '/point/:point_id/delete', component: PointDelete, name: 'point-delete'},
		{path: '/delete-all', component: ClearList, name: 'delete-all'}
	]
});

new Vue({
  router
}).$mount('#app')