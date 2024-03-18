function youtube_parser(url: string): string | null {
  if (url) {
    const regExp =
      /.*(?:youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=)([^#\&\?]*).*/;
    const match = url.match(regExp);
    return match?.[1]?.length === 11 ? match[1] : null;
  }
  return null;
}

export default youtube_parser;
