const dateWithTime = (date: string) => {
  const formattedDate = new Date(date);
  if (isNaN(formattedDate.getTime())) {
    return "Invalid Date";
  }

  const date_format_str =
    formattedDate.getFullYear().toString() +
    "-" +
    ((formattedDate.getMonth() + 1).toString().length == 2
      ? (formattedDate.getMonth() + 1).toString()
      : "0" + (formattedDate.getMonth() + 1).toString()) +
    "-" +
    (formattedDate.getDate().toString().length == 2
      ? formattedDate.getDate().toString()
      : "0" + formattedDate.getDate().toString()) +
    " " +
    (formattedDate.getHours().toString().length == 2
      ? formattedDate.getHours().toString()
      : "0" + formattedDate.getHours().toString()) +
    ":" +
    (formattedDate.getMinutes().toString().length == 2
      ? formattedDate.getMinutes().toString()
      : "0" + formattedDate.getMinutes().toString());

  return date_format_str;
};

export default dateWithTime;
