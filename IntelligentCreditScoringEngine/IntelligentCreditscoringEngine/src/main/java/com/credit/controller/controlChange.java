@GetMapping("/score/{userId}")
public CreditScoreResponse getCreditScore(@PathVariable Long userId) {
    double score = creditScoreService.calculateScore(userId);
    List<String> topFactors = creditScoreService.getTopFactors(userId);
    return new CreditScoreResponse(score, topFactors);
}