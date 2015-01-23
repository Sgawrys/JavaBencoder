JavaBencoder
============

###Currently still a work in progress

Quick and dirty bencoding implementation in Java.

[Bencoding](http://en.wikipedia.org/wiki/Bencode) - Wikipedia entry describing what this does.
[Bencoding from the BitTorrent specification](https://wiki.theory.org/BitTorrentSpecification#Bencoding)

------------

## Examples

**Integer**

4 -> "i4e"

**Strings**

"test" -> "4:test"

**Lists**

("Test", 4, "Bencoding") -> "l4:Testi4e9:Bencodinge"

**Dictionaries**

("Test" : 4,
 "Bencoding" : 5) -> "d4:Testi4e9:Bencodingi5ee"
 
 ------------
 
## What features to complete
 
* ~~Get decoding finished~~