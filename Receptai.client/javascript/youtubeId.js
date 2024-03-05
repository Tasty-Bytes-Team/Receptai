function youtube_parser(url) {
  if (url) {
    var regExp = /.*(?:youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=)([^#\&\?]*).*/;
    var match = url.match(regExp);
    return match && match[1].length == 11 ? match[1] : false;
  }
  return null;
}

export default youtube_parser