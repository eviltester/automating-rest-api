module.exports = {
    millisTitle: millisTitle
  }
  
function millisTitle(context, events, done){
    context.vars['title'] =  'my title ' + new Date().valueOf();
    return done();
}