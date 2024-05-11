const dateWithTime = (date: string) => {
    const formatedDate = new Date(date);
    const date_format_str =
      formatedDate.getFullYear().toString() +
      "-" +
      ((formatedDate.getMonth() + 1).toString().length == 2
        ? (formatedDate.getMonth() + 1).toString()
        : "0" + (formatedDate.getMonth() + 1).toString()) +
      "-" +
      (formatedDate.getDate().toString().length == 2
        ? formatedDate.getDate().toString()
        : "0" + formatedDate.getDate().toString()) +
      " " +
      (formatedDate.getHours().toString().length == 2
        ? formatedDate.getHours().toString()
        : "0" + formatedDate.getHours().toString()) +
      ":" +
      ((formatedDate.getMinutes() / 5) * 5).toString();
    return date_format_str;
  };

  export default dateWithTime