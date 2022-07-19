export interface RankingStudent{
  ipAddress: string;
  sqlInjection: boolean;
  blindSqlInjection: boolean;
  emailWithoutAt: boolean;
  xss: boolean;
  profile_picture: boolean;
  htmlCommentUser: boolean;
  priceOrderBug: boolean;
  guessUserLogin: boolean;
  guessCoupon: boolean;
  deleteUser: boolean;
  commentXss: boolean;
  loginBruteForce: boolean;
  hashUser: boolean;
  securityMisconfiguration: boolean;
  points: number;
}
