const passwordCheck = (password: string) => {
  return (
    password.match(/[a-z]+/) &&
    password.match(/[A-Z]+/) &&
    password.match(/[0-9]+/) &&
    password.match(/[$@#&!?*-~.,/;:]+/)
  );
};

export default passwordCheck;
