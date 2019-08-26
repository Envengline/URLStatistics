#PingCapHomeWork

Hash algorithm is used to divide the original large file into 1000 small files(One file size is about 500M).
Or hash into more and smaller files to meet machine resource constraints.
Because by hashing, I ensure that the same URL can be assigned to the same small file.
Then count each URL from each file, using a hashMap to store the statistics.

Class CreateURL : create test URL data.
Class URLStatis : cut files and count the URLs.
