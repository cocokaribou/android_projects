class Post {
  String content;

  // constructor
  Post({ required this.content });

  @override
  bool operator ==(Object other) {
    return other is Post && other.content == content;
  }
}